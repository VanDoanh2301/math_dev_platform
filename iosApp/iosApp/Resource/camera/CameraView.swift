//
//  CameraView.swift
//  iosApp
//
//  Created by DoanhMac on 11/13/25.
//

import SwiftUI
import AVFoundation
import PhotosUI
import CropViewController


struct CameraView_Previews: PreviewProvider {
    static var previews: some View {
        CameraView(
            onImageCaptured: { image in
                print("Preview captured image: \(image)")
            },
            onBack: {
                print("Preview back tapped")
            }
        )
    }
}


struct CameraView: View {
    var onImageCaptured: ((UIImage) -> Void)?
    var onBack: (() -> Void)?
    
    var body: some View {
        CameraVC(onImageCaptured: onImageCaptured, onBack: onBack)
            .ignoresSafeArea()
    }
}

struct CameraVC: View {
    @StateObject var camera = CameraModel()
    @State private var flashMode: FlashMode = .off
    @State private var selectedImage: UIImage?
    @State private var showCropVC: Bool = false
    @State private var photosPickerItem: PhotosPickerItem?
    @State private var selectedStyle: CameraAIStyle = .math
    @State private var captureIconScale: CGFloat = 1.0
    @State private var styleCenterPositions: [CameraAIStyle: CGFloat] = [:]
    @State private var scrollViewCenterX: CGFloat = 0
    @State private var isProgrammaticScroll = false
    @State private var isUpdatingFromScroll = false
    private let styleSelectorSpace = "styleSelectorSpace"
    
    let croppingOptions = CroppedPhotosPickerOptions(
        doneButtonTitle: "Select",
        doneButtonColor: .orange
    )
    
    var onImageCaptured: ((UIImage) -> Void)?
    var onBack: (() -> Void)?
    
    enum FlashMode {
        case off, on, auto
        
        var iconName: String {
            switch self {
            case .off: return "bolt.slash.fill"
            case .on: return "bolt.fill"
            case .auto: return "bolt.badge.automatic.fill"
            }
        }
        
        var avFlashMode: AVCaptureDevice.FlashMode {
            switch self {
            case .off: return .off
            case .on: return .on
            case .auto: return .auto
            }
        }
        
        mutating func toggle() {
            switch self {
            case .off: self = .on
            case .on: self = .auto
            case .auto: self = .off
            }
        }
    }
    
    var body: some View {
        ZStack {
            CameraPreview(camera: camera)
                .ignoresSafeArea()
            
            VStack {
                Spacer()
                
                if camera.isTaken {
                    previewModeControls
                } else {
                    captureModeControls
                }
            }
            
            backButton
        }
        .sheet(isPresented: $showCropVC) {
            if let image = selectedImage {
                cropViewController(image: image)
            }
        }
        .onAppear {
            camera.Check()
            camera.startSession()
        }
        .onDisappear {
            camera.stopSession()
        }
        .onChange(of: selectedStyle) { _ in
            withAnimation(.spring(response: 0.3, dampingFraction: 0.6)) {
                captureIconScale = 1.15
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.18) {
                withAnimation(.spring(response: 0.35, dampingFraction: 0.7)) {
                    captureIconScale = 1.0
                }
            }
        }
    }
    
    // MARK: - UI Components
    
    private var backButton: some View {
        VStack {
            HStack {
                Button(action: { onBack?() }) {
                    Image(systemName: "chevron.left")
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundStyle(.white)
                        .frame(width: 40, height: 40)
                        .background(Color.black.opacity(0.4))
                        .clipShape(Circle())
                }
                .padding(.top, 50)
                .padding(.leading, 20)
                
                Spacer()
            }
            Spacer()
        }
    }
    
    private var previewModeControls: some View {
        VStack(spacing: 0) {
            HStack {
                Spacer()
                
                Button(action: { camera.reTake() }) {
                    Image(systemName: "arrow.counterclockwise")
                        .font(.system(size: 22, weight: .semibold))
                        .foregroundStyle(.white)
                        .frame(width: 50, height: 50)
                        .background(Color.blue)
                        .clipShape(Circle())
                        .shadow(color: .black.opacity(0.3), radius: 5)
                }
                .padding(.trailing, 20)
                .padding(.top, 20)
            }
            
            Spacer()
            
            HStack {
                Button(action: handleNextButton) {
                    HStack(spacing: 8) {
                        Text("Next")
                            .font(.system(size: 17, weight: .semibold))
                        
                        Image(systemName: "arrow.right")
                            .font(.system(size: 15, weight: .semibold))
                    }
                    .foregroundStyle(.white)
                    .padding(.vertical, 14)
                    .padding(.horizontal, 30)
                    .background(Color.blue)
                    .clipShape(Capsule())
                    .shadow(color: .black.opacity(0.3), radius: 5)
                }
                .padding(.leading, 20)
                .padding(.bottom, 50)
                
                Spacer()
            }
        }
    }
    
    private var captureModeControls: some View {
        VStack(spacing: 24) {
            styleSelector
            HStack(spacing: 50) {
                photoLibraryButton
                captureButton(for: selectedStyle)
                flashButton
            }
        }
        .padding(.bottom, 50)
    }

    private var styleSelector: some View {
        GeometryReader { outerGeo in
            ScrollViewReader { proxy in
                ScrollView(.horizontal, showsIndicators: false) {
                    let sidePadding = max(0, (outerGeo.size.width / 2) - 80)
                    HStack(spacing: 16) {
                        Color.clear.frame(width: sidePadding)
                        ForEach(CameraAIStyle.allCases) { style in
                            let isSelected = style == selectedStyle
                            styleChip(for: style, isSelected: isSelected)
                        }
                        Color.clear.frame(width: sidePadding)
                    }
                }
                .frame(height: 70)
                .coordinateSpace(name: styleSelectorSpace)
                .onAppear {
                    updateScrollViewCenter(with: outerGeo.size.width)
                    centerSelectedStyleIfNeeded(proxy: proxy, animated: false)
                }
                .onChange(of: outerGeo.size.width) { newWidth in
                    updateScrollViewCenter(with: newWidth)
                    updateSelectedStyleFromScroll()
                }
                .onPreferenceChange(StyleCenterPreferenceKey.self) { entries in
                    var updated: [CameraAIStyle: CGFloat] = [:]
                    for entry in entries {
                        updated[entry.style] = entry.center
                    }
                    styleCenterPositions = updated
                    updateSelectedStyleFromScroll()
                }
                .onChange(of: selectedStyle) { _ in
                    centerSelectedStyleIfNeeded(proxy: proxy, animated: true)
                }
            }
        }
        .frame(height: 80)
    }
    
    private var photoLibraryButton: some View {
        CroppedPhotosPicker(
            style: .default,
            options: croppingOptions,
            selection: $selectedImage,
            didCancel: { }
        ) {
            Image(systemName: "photo.on.rectangle")
                .font(.system(size: 30))
                .foregroundStyle(.white)
                .frame(width: 50, height: 50)
                .shadow(color: .black.opacity(0.5), radius: 3)
        }
        .onChange(of: selectedImage) { _, newValue in
            if newValue != nil {
                showCropVC = true
            }
        }
    }
    
    private func captureButton(for style: CameraAIStyle) -> some View {
        Button(action: handleCapture) {
            ZStack {
                Circle()
                    .fill(.white)
                    .frame(width: 75, height: 75)
                
                Circle()
                    .stroke(.white, lineWidth: 4)
                    .frame(width: 90, height: 90)

                Image(systemName: style.iconName)
                    .font(.system(size: 32, weight: .semibold))
                    .foregroundStyle(style.accentColor)
                    .scaleEffect(captureIconScale)
            }
            .shadow(color: .black.opacity(0.3), radius: 5)
        }
    }
    
    private var flashButton: some View {
        Button(action: toggleFlash) {
            Image(systemName: flashMode.iconName)
                .font(.system(size: 30))
                .foregroundStyle(.white)
                .frame(width: 50, height: 50)
                .shadow(color: .black.opacity(0.5), radius: 3)
        }
    }
    
    // MARK: - Actions
    
    private func handleNextButton() {
        guard let capturedImage = UIImage(data: camera.picData) else {
            print("No image captured")
            return
        }
        selectedImage = capturedImage
        showCropVC = true
    }
    
    private func handleCapture() {
        #if targetEnvironment(simulator)
        if let dummyImage = UIImage(systemName: "photo.fill") {
            selectedImage = dummyImage
            showCropVC = true
        }
        #else
        camera.takePic(flashMode: flashMode.avFlashMode)
        #endif
    }
    
    private func toggleFlash() {
        flashMode.toggle()
    }
    
    private func centerSelectedStyleIfNeeded(proxy: ScrollViewProxy, animated: Bool) {
        guard styleCenterPositions[selectedStyle] != nil else { return }
        isProgrammaticScroll = true
        let animation = Animation.spring(response: 0.35, dampingFraction: 0.85)
        if animated {
            withAnimation(animation) {
                proxy.scrollTo(selectedStyle, anchor: .center)
            }
        } else {
            proxy.scrollTo(selectedStyle, anchor: .center)
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.4) {
            isProgrammaticScroll = false
        }
    }
    
    private func updateSelectedStyleFromScroll() {
        guard !isProgrammaticScroll,
              scrollViewCenterX != 0,
              !styleCenterPositions.isEmpty else { return }
        
        if let closest = styleCenterPositions.min(by: {
            abs($0.value - scrollViewCenterX) < abs($1.value - scrollViewCenterX)
        }), closest.key != selectedStyle {
            isUpdatingFromScroll = true
            selectedStyle = closest.key
            DispatchQueue.main.async {
                isUpdatingFromScroll = false
            }
        }
    }
    
    private func cropViewController(image: UIImage) -> some View {
        CropView(
            image: image,
            croppingStyle: .default,
            croppingOptions: croppingOptions
        ) { croppedImage in
            onImageCaptured?(croppedImage.image)
            showCropVC = false
        } didCropToCircularImage: { croppedImage in
            onImageCaptured?(croppedImage.image)
            showCropVC = false
        } didCropImageToRect: { _ in
            showCropVC = false
        } didFinishCancelled: { _ in
            showCropVC = false
        }
        .ignoresSafeArea()
    }
    
    private func updateScrollViewCenter(with width: CGFloat) {
        let newCenter = width / 2
        if abs(newCenter - scrollViewCenterX) > 0.5 {
            scrollViewCenterX = newCenter
        }
    }
}

private struct StyleChipCenterReporter: View {
    let style: CameraAIStyle
    let coordinateSpace: String

    var body: some View {
        GeometryReader { geo in
            Color.clear.preference(
                key: StyleCenterPreferenceKey.self,
                value: [
                    StyleCenterData(
                        style: style,
                        center: geo.frame(in: .named(coordinateSpace)).midX
                    )
                ]
            )
        }
    }
}

extension CameraVC {
    private func styleChip(for style: CameraAIStyle, isSelected: Bool) -> some View {
        Text(style.title.uppercased())
            .font(.system(size: 14, weight: .semibold))
            .foregroundStyle(isSelected ? Color.white : Color.white.opacity(0.65))
            .padding(.vertical, 10)
            .padding(.horizontal, 20)
            .background(
                Capsule()
                    .fill(isSelected ? style.accentColor.opacity(0.8) : Color.black.opacity(0.3))
            )
            .overlay(
                Capsule()
                    .stroke(style.accentColor.opacity(isSelected ? 0.95 : 0.4),
                            lineWidth: isSelected ? 2 : 1)
            )
            .contentShape(Rectangle())
            .id(style)
            .onTapGesture {
                withAnimation(.spring(response: 0.3, dampingFraction: 0.8)) {
                    selectedStyle = style
                }
            }
            .background(
                StyleChipCenterReporter(
                    style: style,
                    coordinateSpace: styleSelectorSpace
                )
            )
    }
}

enum CameraAIStyle: String, CaseIterable, Identifiable {
    case math, chemistry, physic, trans

    var id: String { rawValue }

    var title: String {
        switch self {
        case .math: return "Math"
        case .chemistry: return "Chemistry"
        case .physic: return "Physic"
        case .trans: return "Trans"
        }
    }

    var iconName: String {
        switch self {
        case .math: return "function"
        case .chemistry: return "flask.fill"
        case .physic: return "atom"
        case .trans: return "character.textbox"
        }
    }

    var accentColor: Color {
        switch self {
        case .math: return Color(red: 0.19, green: 0.44, blue: 0.94)
        case .chemistry: return Color(red: 0.11, green: 0.74, blue: 0.58)
        case .physic: return Color(red: 0.96, green: 0.58, blue: 0.2)
        case .trans: return Color(red: 0.55, green: 0.39, blue: 0.92)
        }
    }
}

private struct StyleCenterData: Equatable {
    let style: CameraAIStyle
    let center: CGFloat
}

private struct StyleCenterPreferenceKey: PreferenceKey {
    static var defaultValue: [StyleCenterData] = []

    static func reduce(value: inout [StyleCenterData], nextValue: () -> [StyleCenterData]) {
        var combined = value
        for entry in nextValue() {
            combined.removeAll(where: { $0.style == entry.style })
            combined.append(entry)
        }
        value = combined
    }
}

// MARK: - Camera Preview

struct CameraPreview: UIViewRepresentable {
    @ObservedObject var camera: CameraModel
    
    func makeUIView(context: Context) -> UIView {
        #if targetEnvironment(simulator)
        return createSimulatorView()
        #else
        return createCameraView()
        #endif
    }
    
    func updateUIView(_ uiView: UIView, context: Context) {
        #if !targetEnvironment(simulator)
        DispatchQueue.main.async {
            camera.preview?.frame = uiView.bounds
        }
        #endif
    }
    
    private func createSimulatorView() -> UIView {
        let view = UIView(frame: UIScreen.main.bounds)
        view.backgroundColor = .black
        
        let icon = UIImageView(image: UIImage(systemName: "camera.fill"))
        icon.tintColor = .white.withAlphaComponent(0.3)
        icon.contentMode = .scaleAspectFit
        icon.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(icon)
        
        let label = UILabel()
        label.text = "Camera not available on Simulator"
        label.textColor = .white.withAlphaComponent(0.6)
        label.textAlignment = .center
        label.font = .systemFont(ofSize: 16, weight: .medium)
        label.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(label)
        
        NSLayoutConstraint.activate([
            icon.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            icon.centerYAnchor.constraint(equalTo: view.centerYAnchor, constant: -30),
            icon.widthAnchor.constraint(equalToConstant: 80),
            icon.heightAnchor.constraint(equalToConstant: 80),
            
            label.topAnchor.constraint(equalTo: icon.bottomAnchor, constant: 20),
            label.centerXAnchor.constraint(equalTo: view.centerXAnchor)
        ])
        
        return view
    }
    
    private func createCameraView() -> UIView {
        let view = UIView(frame: UIScreen.main.bounds)
        camera.preview = AVCaptureVideoPreviewLayer(session: camera.session)
        camera.preview.frame = view.frame
        camera.preview.videoGravity = .resizeAspectFill
        view.layer.addSublayer(camera.preview)
        return view
    }
}
