package com.dev.devmath

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.dev.devmath.core.platform.utils.LocalNativeViewFactory
import com.dev.devmath.core.platform.utils.NativeViewFactory


fun MainViewController(
    nativeViewFactory: NativeViewFactory
) = ComposeUIViewController {
    CompositionLocalProvider(LocalNativeViewFactory provides nativeViewFactory) {
        App()
    }
}