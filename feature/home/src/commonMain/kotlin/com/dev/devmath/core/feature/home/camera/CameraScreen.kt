package com.dev.devmath.core.feature.home.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.camera.CameraController
import com.dev.devmath.core.camera.CameraPreview
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.icon.AppIcons
import com.dev.devmath.core.designsystem.theme.KptTheme

/**
 * Camera screen with preview and capture button
 */
@Composable
fun CameraScreen(
    onBackClick: () -> Unit,
    onPhotoCaptured: (ByteArray?) -> Unit = {}
) {
    var cameraController: CameraController? by remember { mutableStateOf(null) }
    var isCapturing by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Camera preview
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            onControllerReady = { controller ->
                cameraController = controller
            }
        )
        
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(KptTheme.spacing.md)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = AppIcons.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        
        // Capture button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = KptTheme.spacing.xl)
        ) {
            Button(
                onClick = {
                    if (!isCapturing && cameraController != null) {
                        isCapturing = true
                        cameraController?.capturePhoto { photoBytes ->
                            isCapturing = false
                            onPhotoCaptured(photoBytes)
                            if (photoBytes != null) {
                                onBackClick()
                            }
                        }
                    }
                },
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                enabled = !isCapturing && cameraController != null
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = if (isCapturing) Color.Gray else Color.White,
                            shape = CircleShape
                        )
                )
            }
        }
    }
    
    // Release camera when screen is disposed
    DisposableEffect(cameraController) {
        onDispose {
            cameraController?.release()
        }
    }
}

