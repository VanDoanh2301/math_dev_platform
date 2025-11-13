//
// Created by DoanhMac on 11/13/25.
//

import Foundation
import AVFoundation
import UIKit
import SwiftUI

class CameraModel: NSObject, ObservableObject, AVCapturePhotoCaptureDelegate {
    
    @Published var isTaken = false
    @Published var session = AVCaptureSession()
    @Published var alert = false
    @Published var output = AVCapturePhotoOutput()
    @Published var preview: AVCaptureVideoPreviewLayer!
    @Published var isSaved = false
    @Published var picData = Data(count: 0)
    
    private let sessionQueue = DispatchQueue(label: "camera.session.queue")
    private var deviceInput: AVCaptureDeviceInput?
    
    override init() {
        super.init()
        #if !targetEnvironment(simulator)
        checkPermission()
        #endif
    }
    
    func checkPermission() {
        switch AVCaptureDevice.authorizationStatus(for: .video) {
        case .authorized:
            setUp()
        case .notDetermined:
            AVCaptureDevice.requestAccess(for: .video) { [weak self] granted in
                guard let self = self else { return }
                if granted {
                    self.setUp()
                } else {
                    DispatchQueue.main.async {
                        self.alert = true
                    }
                }
            }
        case .denied, .restricted:
            DispatchQueue.main.async {
                self.alert = true
            }
        @unknown default:
            DispatchQueue.main.async {
                self.alert = true
            }
        }
    }
    
    func setUp() {
        sessionQueue.async { [weak self] in
            guard let self = self else { return }
            
            self.session.beginConfiguration()
            defer { self.session.commitConfiguration() }
            
            // Find available camera device
            guard let device = self.findCameraDevice() else {
                DispatchQueue.main.async {
                    self.alert = true
                }
                return
            }
            
            // Add input
            do {
                let input = try AVCaptureDeviceInput(device: device)
                self.deviceInput = input // Store strong reference
                if self.session.canAddInput(input) {
                    self.session.addInput(input)
                } else {
                    return
                }
            } catch {
                DispatchQueue.main.async {
                    self.alert = true
                }
                return
            }
            
            // Add output
            if self.session.canAddOutput(self.output) {
                self.session.addOutput(self.output)
            } else {
                return
            }
            
            // Start session on main thread
            DispatchQueue.main.async {
                if !self.session.isRunning {
                    self.session.startRunning()
                }
            }
        }
    }
    
    private func findCameraDevice() -> AVCaptureDevice? {
        if #available(iOS 13.0, *) {
            return AVCaptureDevice.default(.builtInTripleCamera, for: .video, position: .back) ??
                   AVCaptureDevice.default(.builtInDualWideCamera, for: .video, position: .back) ??
                   AVCaptureDevice.default(.builtInDualCamera, for: .video, position: .back) ??
                   AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back)
        } else {
            return AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back)
        }
    }
    
    func takePic() {
        guard session.isRunning else {
            return
        }
        
        sessionQueue.async { [weak self] in
            guard let self = self else { return }
            let settings = AVCapturePhotoSettings()
            
            // Ensure delegate is set on main thread
            DispatchQueue.main.async {
                self.output.capturePhoto(with: settings, delegate: self)
                self.isTaken = true
            }
        }
    }
    
    func reTake() {
        sessionQueue.async { [weak self] in
            guard let self = self else { return }
            
            DispatchQueue.main.async {
                self.picData = Data(count: 0)
                withAnimation {
                    self.isTaken = false
                }
                self.isSaved = false
                
                // Restart session if needed
                if !self.session.isRunning {
                    self.session.startRunning()
                }
            }
        }
    }
    
    // MARK: - AVCapturePhotoCaptureDelegate
    
    func photoOutput(_ output: AVCapturePhotoOutput, didFinishProcessingPhoto photo: AVCapturePhoto, error: Error?) {
        if let error = error {
            DispatchQueue.main.async {
                self.alert = true
            }
            return
        }
        
        guard let imageData = photo.fileDataRepresentation() else {
            DispatchQueue.main.async {
                self.alert = true
            }
            return
        }
        
        DispatchQueue.main.async {
            self.picData = imageData
            self.session.stopRunning()
        }
    }
    
    func savePic() {
        guard !picData.isEmpty, let image = UIImage(data: picData) else {
            return
        }
        
        UIImageWriteToSavedPhotosAlbum(image, nil, nil, nil)
        isSaved = true
    }
    
    deinit {
        // Stop session synchronously on the session queue
        let session = self.session
        let queue = self.sessionQueue
        
        // Use sync to ensure cleanup completes before deallocation
        queue.sync {
            if session.isRunning {
                session.stopRunning()
            }
            
            // Remove all inputs and outputs
            session.beginConfiguration()
            let inputs = session.inputs
            let outputs = session.outputs
            
            inputs.forEach { session.removeInput($0) }
            outputs.forEach { session.removeOutput($0) }
            
            session.commitConfiguration()
        }
        
        // Clear preview (will be handled automatically by layer hierarchy)
        preview?.removeFromSuperlayer()
        preview = nil
        
        // Clear device input reference
        deviceInput = nil
    }
}
