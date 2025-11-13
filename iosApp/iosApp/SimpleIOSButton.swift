//
// Created by DoanhMac on 11/13/25.
//

import Foundation
import ComposeApp
import SwiftUI

struct SimpleIOSButton: View {
    var label: String
    var action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(label)
                .font(.headline)
        }
    }
}

class IOSNativeViewFactory: NativeViewFactory {
    static var shared = IOSNativeViewFactory()
    func createButtonView(label: String, onClick: @escaping () -> Void) -> UIViewController {
        let view = SimpleIOSButton(label: label, action: onClick)

        return UIHostingController(rootView: view)
    }

}