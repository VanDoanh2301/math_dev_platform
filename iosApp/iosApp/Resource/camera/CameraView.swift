//
//  CameraView.swift
//  iosApp
//
//  Created by DoanhMac on 11/13/25.
//

import SwiftUI
import AVFoundation
import PhotosUI

struct CameraView: View {
    var onImageCaptured: ((UIImage) -> Void)?
    var onBack: (() -> Void)?
    
    var body: some View {
        CameraVC(onImageCaptured: onImageCaptured, onBack: onBack)
    }
}

struct CameraVC: View {
    @StateObject private var camera = CameraModel()
    @State private var flashMode: FlashMode = .off
    @State private var selectedImage: UIImage?
    @State private var photosPickerItem: PhotosPickerItem?
    
    var onImageCaptured: ((UIImage) -> Void)?
    var onBack: (() -> Void)?
    
    enum FlashMode {
        case off
        case on
        case auto
    }
    
    var body: some View {
        ZStack {
            CameraPreview(camera: camera)
                .ignoresSafeArea()
            
            VStack {
                Spacer()
                
                if camera.isTaken {
                    capturedPhotoControls
                } else {
                    cameraControls
                }
            }
            
            topControls
        }
        .onAppear {
            #if !targetEnvironment(simulator)
            camera.checkPermission()
            #endif
        }
        .alert("Camera Permission Required", isPresented: $camera.alert) {
            Button("Settings") {
                if let settingsUrl = URL(string: UIApplication.openSettingsURLString) {
                    UIApplication.shared.open(settingsUrl)
                }
            }
            Button("Cancel", role: .cancel) { }
        } message: {
            Text("Please enable camera access in Settings to use this feature.")
        }
    }
    
    // MARK: - Top Controls
    
    private var topControls: some View {
        VStack {
            HStack {
                Button(action: {
                    onBack?()
                }) {
                    Image(systemName: "chevron.left")
                        .font(.system(size: 20, weight: .semibold))
                        .foregroundColor(.white)
                        .padding(12)
                        .background(Color.black.opacity(0.3))
                        .clipShape(Circle())
                }
                .padding(.horizontal, 16)
                .padding(.top, 8)
                
                Spacer()
            }
            Spacer()
        }
    }
    
    // MARK: - Camera Controls
    
    private var cameraControls: some View {
        HStack(spacing: 40) {
            photoLibraryButton
            
            captureButton
            
            flashButton
        }
        .padding(.bottom, 40)
    }
    
    private var photoLibraryButton: some View {
        CroppedPhotosPicker(
            style: .default,
            options: .init(),
            selection: $selectedImage
        ) { rect in
            // Handle crop if needed
        } didCancel: {
            // Handle cancel
        } label: {
            Image(systemName: "photo.on.rectangle")
                .font(.system(size: 24))
                .foregroundColor(.white)
                .frame(width: 44, height: 44)
                .background(Color.black.opacity(0.3))
                .clipShape(Circle())
        }
        .onChange(of: selectedImage) { oldValue, newValue in
            if let image = newValue {
                onImageCaptured?(image)
            }
        }
    }
    
    private var captureButton: some View {
        Button {
            #if targetEnvironment(simulator)
            // Simulator: Use placeholder image
            if let dummyImage = UIImage(systemName: "camera.fill") {
                onImageCaptured?(dummyImage)
            }
            #else
            camera.takePic()
            #endif
        } label: {
            ZStack {
                Circle()
                    .fill(Color.white)
                    .frame(width: 65, height: 65)
                Circle()
                    .stroke(Color.white, lineWidth: 4)
                    .frame(width: 75, height: 75)
            }
        }
    }
    
    private var flashButton: some View {
        Button {
            flashMode = changeFlashMode()
        } label: {
            Image(systemName: flashIconName)
                .font(.system(size: 24))
                .foregroundColor(.white)
                .frame(width: 44, height: 44)
                .background(Color.black.opacity(0.3))
                .clipShape(Circle())
        }
    }
    
    // MARK: - Captured Photo Controls
    
    private var capturedPhotoControls: some View {
        VStack {
            HStack {
                Spacer()
                
                Button {
                    camera.reTake()
                } label: {
                    Image(systemName: "arrow.triangle.2.circlepath.camera")
                        .font(.system(size: 20))
                        .foregroundColor(.white)
                        .padding(12)
                        .background(Color.blue)
                        .clipShape(Circle())
                }
                .padding(.trailing, 16)
                .padding(.top, 16)
            }
            
            Spacer()
            
            HStack {
                Button {
                    if let capturedImage = UIImage(data: camera.picData) {
                        onImageCaptured?(capturedImage)
                    }
                } label: {
                    Text("Next")
                        .foregroundColor(.white)
                        .fontWeight(.semibold)
                        .padding(.vertical, 12)
                        .padding(.horizontal, 24)
                        .background(Color.blue)
                        .clipShape(Capsule())
                }
                .padding(.leading, 16)
                .padding(.bottom, 40)
                
                Spacer()
            }
        }
    }
    
    // MARK: - Helper Methods
    
    private func changeFlashMode() -> FlashMode {
        switch flashMode {
        case .off:
            setFlash(mode: .on)
            return .on
        case .on:
            setFlash(mode: .auto)
            return .auto
        case .auto:
            setFlash(mode: .off)
            return .off
        }
    }
    
    private var flashIconName: String {
        switch flashMode {
        case .on:
            return "bolt.fill"
        case .off:
            return "bolt.slash.fill"
        case .auto:
            return "bolt.badge.automatic.fill"
        }
    }
    
    private func setFlash(mode: FlashMode) {
        guard let device = AVCaptureDevice.default(for: .video),
              device.hasTorch else {
            return
        }
        
        do {
            try device.lockForConfiguration()
            defer { device.unlockForConfiguration() }
            
            switch mode {
            case .on:
                device.torchMode = .on
            case .off:
                device.torchMode = .off
            case .auto:
                device.torchMode = .auto
            }
        } catch {
            // Handle error silently
        }
    }
}

// MARK: - Camera Preview

struct CameraPreview: UIViewRepresentable {
    @ObservedObject var camera: CameraModel
    
    func makeUIView(context: Context) -> UIView {
        #if targetEnvironment(simulator)
        let view = UIView(frame: UIScreen.main.bounds)
        view.backgroundColor = UIColor.black
        
        let label = UILabel()
        label.text = "Camera Preview\n(Not available on Simulator)"
        label.textColor = .white
        label.textAlignment = .center
        label.numberOfLines = 0
        label.font = .systemFont(ofSize: 16)
        label.frame = view.bounds
        view.addSubview(label)
        
        return view
        #else
        let view = UIView(frame: UIScreen.main.bounds)
        camera.preview = AVCaptureVideoPreviewLayer(session: camera.session)
        camera.preview.frame = view.frame
        camera.preview.videoGravity = .resizeAspectFill
        view.layer.addSublayer(camera.preview)
        
        return view
        #endif
    }
    
    func updateUIView(_ uiView: UIView, context: Context) {
        #if !targetEnvironment(simulator)
        if let preview = camera.preview {
            CATransaction.begin()
            CATransaction.setDisableActions(true)
            preview.frame = uiView.bounds
            CATransaction.commit()
        }
        #endif
    }
}

// MARK: - Preview

#Preview {
    CameraView()
}
