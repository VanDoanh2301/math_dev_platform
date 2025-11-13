package com.dev.devmath.core.platform.utils

import platform.UIKit.UIViewController

interface NativeViewFactory {
    fun createCameraView(): UIViewController
}

