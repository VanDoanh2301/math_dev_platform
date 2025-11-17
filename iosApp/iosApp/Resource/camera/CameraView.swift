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
        HStack(spacing: 50) {
            photoLibraryButton
            captureButton
            flashButton
        }
        .padding(.bottom, 50)
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
    
    private var captureButton: some View {
        Button(action: handleCapture) {
            ZStack {
                Circle()
                    .fill(.white)
                    .frame(width: 75, height: 75)
                
                Circle()
                    .stroke(.white, lineWidth: 4)
                    .frame(width: 90, height: 90)
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
