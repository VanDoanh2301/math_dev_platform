package com.dev.devmath.core.platform.utils
import androidx.compose.runtime.staticCompositionLocalOf

val LocalNativeViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("No view factory provided.")
}