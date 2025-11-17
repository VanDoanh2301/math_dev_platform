import Foundation
import ComposeApp
import SwiftUI
import UIKit

class IOSNativeViewFactory: NativeViewFactory {
    
    static var shared = IOSNativeViewFactory()
    
    private init() {}
    
    func createCameraView(
        onImageCaptured: @escaping (KotlinByteArray) -> Void,
        onBack: @escaping () -> Void
    ) -> UIViewController {
        

        let view = CameraView(
            onImageCaptured: { [weak self] uiImage in
                self?.handleImageCapture(uiImage: uiImage, callback: onImageCaptured)
            },
            onBack: {
                onBack()
            }
        )
        
        let hostingController = UIHostingController(rootView: view)
        hostingController.modalPresentationStyle = .fullScreen
        
        return hostingController
    }
    
    private func handleImageCapture(
        uiImage: UIImage,
        callback: @escaping (KotlinByteArray) -> Void
    ) {
        DispatchQueue.main.async {
            guard let imageData = uiImage.jpegData(compressionQuality: 0.8) else {
                print("❌ Failed to convert UIImage to JPEG data")
                return
            }
            
            print("✅ Image converted, size: \(imageData.count) bytes")
            
            // Convert Data to KotlinByteArray
            let byteArray = KotlinByteArray.from(data: imageData)
            
            print("✅ KotlinByteArray created, size: \(byteArray.size)")
            
            // Call the callback
            callback(byteArray)
            
            print("✅ Callback invoked")
        }
    }
}

// MARK: - KotlinByteArray Extension

extension KotlinByteArray {
    static func from(data: Data) -> KotlinByteArray {
        let size = Int32(data.count)
        let result = KotlinByteArray(size: size)
        
        // ✅ Efficient conversion using withUnsafeBytes
        data.withUnsafeBytes { (rawBufferPointer: UnsafeRawBufferPointer) in
            guard let baseAddress = rawBufferPointer.baseAddress else {
                return
            }
            
            let int8Pointer = baseAddress.assumingMemoryBound(to: Int8.self)
            
            for i in 0..<data.count {
                result.set(index: Int32(i), value: int8Pointer[i])
            }
        }
        
        return result
    }
    
    // ✅ Alternative: Direct conversion (faster for large images)
    static func fromFast(data: Data) -> KotlinByteArray {
        let size = Int32(data.count)
        let result = KotlinByteArray(size: size)
        
        var index: Int32 = 0
        for byte in data {
            result.set(index: index, value: Int8(bitPattern: byte))
            index += 1
        }
        
        return result
    }
    
    // ✅ Helper: Convert back to Data
    func toData() -> Data {
        var data = Data()
        let count = Int(self.size)
        
        for i in 0..<count {
            let byte = self.get(index: Int32(i))
            data.append(UInt8(bitPattern: byte))
        }
        
        return data
    }
}

