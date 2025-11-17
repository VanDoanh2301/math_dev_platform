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
    
    func Check() {
        switch AVCaptureDevice.authorizationStatus(for: .video) {
        case .authorized:
            setUp()
            return
        case .notDetermined:
            AVCaptureDevice.requestAccess(for: .video) { (status) in
                if status {
                    self.setUp()
                }
            }
        case .denied:
            self.alert.toggle()
            return
        default:
            return
        }
    }
    
    func setUp() {
        do {
            self.session.beginConfiguration()
            
            var device: AVCaptureDevice?
            if #available(iOS 13.0, *) {
                device = AVCaptureDevice.default(.builtInTripleCamera, for: .video, position: .back) ??
                         AVCaptureDevice.default(.builtInDualWideCamera, for: .video, position: .back) ??
                         AVCaptureDevice.default(.builtInDualCamera, for: .video, position: .back) ??
                         AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back)
            } else {
                device = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .back)
            }
            
            guard let captureDevice = device else {
                print("Không thể tìm thấy camera")
                return
            }
            
            let input = try AVCaptureDeviceInput(device: captureDevice)
            
            if self.session.canAddInput(input) {
                self.session.addInput(input)
            }
            if self.session.canAddOutput(self.output) {
                self.session.addOutput(self.output)
            }
            
            self.session.commitConfiguration()
            
        } catch {
            print("Camera setup error: \(error.localizedDescription)")
        }
    }
    
    func startSession() {
        DispatchQueue.global(qos: .userInitiated).async {
            if !self.session.isRunning {
                self.session.startRunning()
            }
        }
    }
    
    func stopSession() {
        DispatchQueue.global(qos: .userInitiated).async {
            if self.session.isRunning {
                self.session.stopRunning()
            }
        }
    }
    
    func takePic(flashMode: AVCaptureDevice.FlashMode = .off) {
        DispatchQueue.global(qos: .background).async {
            let photoSettings = AVCapturePhotoSettings()
            
            // Chỉ set flash mode nếu device hỗ trợ
            if self.output.supportedFlashModes.contains(flashMode) {
                photoSettings.flashMode = flashMode
            }
            
            self.output.capturePhoto(with: photoSettings, delegate: self)
            
            DispatchQueue.main.async {
                self.isTaken = true
            }
        }
    }
    
    func reTake() {
        DispatchQueue.main.async {
            self.picData = Data(count: 0)
            withAnimation {
                self.isTaken = false
            }
            self.isSaved = false
            
            // Restart camera session
            self.startSession()
        }
    }
    
    func photoOutput(_ output: AVCapturePhotoOutput, didFinishProcessingPhoto photo: AVCapturePhoto, error: Error?) {
        if let error = error {
            print("Photo capture error: \(error.localizedDescription)")
            return
        }
        
        print("Photo taken successfully")
        
        guard let imageData = photo.fileDataRepresentation() else {
            print("Failed to get image data")
            return
        }
        
        self.picData = imageData
        print("Photo data captured: \(imageData.count) bytes")
        
        // Stop session after capture
        self.stopSession()
    }
    
    func savePic() {
        guard let image = UIImage(data: self.picData) else {
            print("Failed to create image from data")
            return
        }
        
        UIImageWriteToSavedPhotosAlbum(image, nil, nil, nil)
        self.isSaved = true
        print("Photo saved successfully")
    }
}
