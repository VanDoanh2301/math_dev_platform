package com.dev.devmath.core.platform
import androidx.compose.runtime.staticCompositionLocalOf

val LocalNativeViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("No view factory provided.")
}