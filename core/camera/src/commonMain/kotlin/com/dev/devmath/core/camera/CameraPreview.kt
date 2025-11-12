package com.dev.devmath.core.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

/**
 * Local composition variable for camera controller
 */
val LocalCameraController = compositionLocalOf<CameraController?> { null }

/**
 * Composable function to display camera preview
 * Platform-specific implementations will use AndroidView/UIKitView
 * The controller is created internally and provided via LocalCameraController
 */
@Composable
expect fun CameraPreview(
    modifier: Modifier = Modifier,
    onControllerReady: (CameraController) -> Unit = {}
)

