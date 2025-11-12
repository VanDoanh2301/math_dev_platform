package com.dev.devmath.core.camera

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.coroutines.delay
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraPreview(
    modifier: Modifier,
    onControllerReady: (CameraController) -> Unit
) {
    val previewView = remember {
        UIView()
    }

    // Create controller with preview view
    val cameraController = remember(previewView) {
        CameraController.create(previewView)
    }

    // Notify parent that controller is ready
    LaunchedEffect(Unit) {
        onControllerReady(cameraController)
    }
    
    // Start camera preview with delay to avoid race conditions with Compose render cycle
    LaunchedEffect(Unit) {
        // Delay to ensure UIKitView is fully initialized before starting camera
        // This prevents "Already resumed" errors by avoiding conflicts with render cycle
        delay(400)
        try {
            cameraController.startCameraPreview()
        } catch (e: Exception) {
            // Silently handle errors (e.g., on simulator where camera is not available)
            // This is expected behavior on iOS simulator - camera is not available
            e.printStackTrace()
        }
    }

    // Stop preview and release resources when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            try {
                cameraController.stopCameraPreview()
                cameraController.release()
            } catch (e: Exception) {
                // Silently handle cleanup errors
                e.printStackTrace()
            }
        }
    }

    // Provide controller via composition local
    CompositionLocalProvider(LocalCameraController provides cameraController) {
        UIKitView(
            factory = { previewView },
            modifier = modifier.fillMaxSize(),
            update = { view ->
                // Update preview layer frame when view bounds change
                try {
                    val viewBounds = view.bounds
                    viewBounds.useContents {
                        val bounds = CGRectMake(origin.x, origin.y, size.width, size.height)
                        cameraController.updatePreviewFrame(bounds)
                    }
                } catch (e: Exception) {
                    // Silently handle frame update errors (e.g., on simulator)
                    e.printStackTrace()
                }
            }
        )
    }
}

