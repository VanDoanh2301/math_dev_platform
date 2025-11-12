package com.dev.devmath.core.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.view.PreviewView

@Composable
actual fun CameraPreview(
    modifier: Modifier,
    onControllerReady: (CameraController) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }
    
    // Create controller with preview view
    val cameraController = remember(previewView) {
        CameraController.create(context, lifecycleOwner, previewView)
    }
    
    // Notify parent that controller is ready
    LaunchedEffect(cameraController) {
        onControllerReady(cameraController)
    }
    
    // Start camera preview when controller is ready
    LaunchedEffect(cameraController) {
        cameraController.startCameraPreview()
    }
    
    // Stop preview when composable is disposed
    DisposableEffect(cameraController) {
        onDispose {
            cameraController.stopCameraPreview()
        }
    }
    
    // Provide controller via composition local
    CompositionLocalProvider(LocalCameraController provides cameraController) {
        AndroidView(
            factory = { previewView },
            modifier = modifier.fillMaxSize()
        )
    }
}

