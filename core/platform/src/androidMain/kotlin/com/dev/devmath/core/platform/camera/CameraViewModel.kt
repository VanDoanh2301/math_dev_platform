package com.dev.devmath.core.platform.camera

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CameraViewModel : ViewModel() {

    private val _cameraState = MutableStateFlow(CameraState())
    val cameraState: StateFlow<CameraState> = _cameraState.asStateFlow()

    fun updateFlashMode(mode: Int) {
        _cameraState.value = _cameraState.value.copy(flashMode = mode)
    }

    fun setImageCapture(capture: ImageCapture?) {
        _cameraState.value = _cameraState.value.copy(imageCapture = capture)
    }

    fun setCapturedImage(uri: Uri?) {
        _cameraState.value = _cameraState.value.copy(
            capturedImageUri = uri,
            isTaken = uri != null
        )
    }

    fun retakePicture() {
        _cameraState.value = _cameraState.value.copy(
            capturedImageUri = null,
            isTaken = false
        )
    }

    fun toggleFlash() {
        val newMode = when (_cameraState.value.flashMode) {
            ImageCapture.FLASH_MODE_OFF -> ImageCapture.FLASH_MODE_ON
            ImageCapture.FLASH_MODE_ON -> ImageCapture.FLASH_MODE_AUTO
            else -> ImageCapture.FLASH_MODE_OFF
        }
        updateFlashMode(newMode)
    }
}

data class CameraState(
    val flashMode: Int = ImageCapture.FLASH_MODE_OFF,
    val imageCapture: ImageCapture? = null,
    val capturedImageUri: Uri? = null,
    val isTaken: Boolean = false,
    val isSessionRunning: Boolean = false
)
