//
// Created by DoanhMac on 11/13/25.
//

import Foundation
import ComposeApp
import SwiftUI


class IOSNativeViewFactory: NativeViewFactory {
    func createCameraView() -> UIViewController {
        let view = CameraView()
              
        return UIHostingController(rootView: view)
        
    }

    static var shared = IOSNativeViewFactory()

}
