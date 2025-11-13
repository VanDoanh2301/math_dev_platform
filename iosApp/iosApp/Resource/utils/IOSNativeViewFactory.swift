//
// Created by DoanhMac on 11/13/25.
//

import Foundation
import ComposeApp
import SwiftUI
import UIKit


class IOSNativeViewFactory: NativeViewFactory {
    func createCameraView(
        onImageCaptured: @escaping (KotlinByteArray) -> Void,
        onBack: @escaping () -> Void
    ) -> UIViewController {
        let view = CameraView { uiImage in
            guard let imageData = uiImage.jpegData(compressionQuality: 0.8) else {
                return
            }

            let byteArray = KotlinByteArray.from(data: imageData)
            onImageCaptured(byteArray)
        } onBack: {
            onBack()
        }
        
        return UIHostingController(rootView: view)
    }

    static var shared = IOSNativeViewFactory()
}



extension KotlinByteArray {
    static func from(data: Data) -> KotlinByteArray {
        let int8array = [UInt8](data)
            .map(Int8.init(bitPattern:))
        
        let result = KotlinByteArray(size: Int32(data.count))
        for i in 0..<data.count {
            result.set(index: Int32(i), value: int8array[i])
        }
        
        return result
    }
}


