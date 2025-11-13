package com.dev.devmath.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitViewController
import com.dev.devmath.core.platform.utils.LocalNativeViewFactory

@Composable
actual fun NativeCameraView(
    onImageCaptured: (ByteArray) -> Unit,
    onError: (Throwable) -> Unit, modifier: Modifier
) {
    val factory = LocalNativeViewFactory.current
    UIKitViewController(
        modifier = modifier,
        factory = {
            factory.createCameraView(
                onImageCaptured = onImageCaptured,  // Pass ByteArray directly
                onBack = { /* Handle back if needed */ }
            )
        }
    )
}