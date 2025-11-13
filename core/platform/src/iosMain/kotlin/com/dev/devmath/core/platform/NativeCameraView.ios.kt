package com.dev.devmath.core.platform

import androidx.compose.runtime.Composable

@Composable
actual fun NativeCameraView(
    onImageCaptured: (ByteArray) -> Unit,
    onError: (Throwable) -> Unit
) {

}