package com.dev.devmath.core.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

internal class AndroidCameraController(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val previewView: PreviewView
) {
    private var cameraProvider: ProcessCameraProvider? = null
    private var imageCapture: ImageCapture? = null
    private val executor: Executor = ContextCompat.getMainExecutor(context)

    fun startCameraPreview() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        
        cameraProviderFuture.addListener({
            try {
                val provider = cameraProviderFuture.get()
                cameraProvider = provider
                
                // Build preview use case
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                
                // Build image capture use case
                imageCapture = ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                    .build()
                
                // Select back camera as default
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                
                // Unbind use cases before rebinding
                provider.unbindAll()
                
                // Bind use cases to camera
                provider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, executor)
    }
    
    fun stopCameraPreview() {
        cameraProvider?.unbindAll()
    }
    
    fun capturePhoto(callback: (ByteArray?) -> Unit) {
        val imageCapture = imageCapture ?: run {
            callback(null)
            return
        }
        
        // Create output file in cache directory
        val photoFile = java.io.File(
            context.cacheDir,
            "camera_capture_${System.currentTimeMillis()}.jpg"
        )
        
        // Create output file options
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        
        imageCapture.takePicture(
            outputFileOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    try {
                        // Read file content
                        photoFile.inputStream().use { inputStream ->
                            val bytes = inputStream.readBytes()
                            callback(bytes)
                            // Clean up temp file
                            try {
                                photoFile.delete()
                            } catch (e: Exception) {
                                // Ignore cleanup errors
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        callback(null)
                    }
                }
                
                override fun onError(exception: ImageCaptureException) {
                    exception.printStackTrace()
                    callback(null)
                }
            }
        )
    }
    
    fun release() {
        stopCameraPreview()
        cameraProvider = null
        imageCapture = null
    }
}

actual class CameraController internal constructor(
    private val androidController: AndroidCameraController
) {
    actual fun startCameraPreview() {
        androidController.startCameraPreview()
    }
    
    actual fun stopCameraPreview() {
        androidController.stopCameraPreview()
    }
    
    actual fun capturePhoto(callback: (ByteArray?) -> Unit) {
        androidController.capturePhoto(callback)
    }
    
    actual fun release() {
        androidController.release()
    }
    
    companion object {
        fun create(
            context: Context,
            lifecycleOwner: LifecycleOwner,
            previewView: PreviewView
        ): CameraController {
            val androidController = AndroidCameraController(context, lifecycleOwner, previewView)
            return CameraController(androidController)
        }
    }
}

