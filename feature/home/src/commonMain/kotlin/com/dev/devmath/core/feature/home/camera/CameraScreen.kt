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
import com.dev.devmath.core.platform.NativeCameraView

/**
 * Camera screen with preview and capture button
 */
@Composable
fun CameraScreen(
    onBackClick: () -> Unit,
    onPhotoCaptured: (ByteArray?) -> Unit = {}
) {
    NativeCameraView(
        onImageCaptured = { bytes ->
            onPhotoCaptured(bytes)
        },
        onError = { e ->

        },
        modifier = Modifier.fillMaxSize(),
        onBackClick = {
            onBackClick()
        }
    )

}


