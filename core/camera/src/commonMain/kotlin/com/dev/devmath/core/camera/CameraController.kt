package com.dev.devmath.core.camera

/**
 * Platform-agnostic interface for camera operations
 */
expect class CameraController {
    /**
     * Starts the camera preview
     */
    fun startCameraPreview()
    
    /**
     * Stops the camera preview
     */
    fun stopCameraPreview()
    
    /**
     * Captures a photo
     * @param callback Called with the captured image as ByteArray, or null if capture failed
     */
    fun capturePhoto(callback: (ByteArray?) -> Unit)
    
    /**
     * Releases camera resources
     */
    fun release()
}

