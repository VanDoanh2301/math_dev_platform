package com.dev.devmath.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun NativeCameraView(
    onImageCaptured: (ByteArray) -> Unit,
    onError: (Throwable) -> Unit, modifier: Modifier,
    onBackClick: () -> Unit,
) {

}