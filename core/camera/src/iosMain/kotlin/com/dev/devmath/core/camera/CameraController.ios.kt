package com.dev.devmath.core.camera

import platform.AVFoundation.*
import platform.Foundation.*
import platform.UIKit.*
import platform.QuartzCore.CALayer
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGSize
import platform.CoreGraphics.CGSizeMake
import kotlinx.cinterop.CValue
import kotlinx.cinterop.readValue
import platform.darwin.NSObject
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.cinterop.useContents

@OptIn(ExperimentalForeignApi::class)
internal class IOSCameraController {
    private var captureSession: AVCaptureSession? = null
    private var captureDevice: AVCaptureDevice? = null
    private var captureInput: AVCaptureDeviceInput? = null
    private var captureOutput: AVCapturePhotoOutput? = null
    private var previewLayer: AVCaptureVideoPreviewLayer? = null
    private var previewView: UIView? = null
    private var photoDelegate: PhotoCaptureDelegate? = null
    
    fun setupCamera(previewView: UIView) {
        this.previewView = previewView
        
        // Create capture session
        captureSession = AVCaptureSession().apply {
            sessionPreset = AVCaptureSessionPresetPhoto
        }
        
        // Get back camera - on simulator this will be null
        captureDevice = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
        
        if (captureDevice == null) {
            // Camera not available (e.g., on simulator) - return early
            // This is expected on iOS simulator, so we just return without error
            return
        }
        
        // Create input
        captureInput = try {
            AVCaptureDeviceInput.deviceInputWithDevice(captureDevice!!, null)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        
        if (captureInput == null || captureSession == null) {
            return
        }
        
        // Create output
        captureOutput = AVCapturePhotoOutput()
        
        // Configure session
        captureSession?.apply {
            if (canAddInput(captureInput!!)) {
                addInput(captureInput!!)
            }
            if (canAddOutput(captureOutput!!)) {
                addOutput(captureOutput!!)
            }
        }
        
        // Setup preview layer
        previewLayer = AVCaptureVideoPreviewLayer(session = captureSession!!).apply {
            videoGravity = AVLayerVideoGravityResizeAspectFill
        }
        
        // Add preview layer to view
        // AVCaptureVideoPreviewLayer will automatically fill the view
        if (previewLayer != null) {
            previewView.layer.addSublayer(previewLayer!!)
            // Set frame to match view bounds - AVCaptureVideoPreviewLayer handles layout automatically
            // We'll update it when view layout changes via updatePreviewFrame
        }
    }
    
    fun startCameraPreview() {
        // Check if session and device are available before starting
        if (captureSession == null || captureDevice == null) {
            return
        }
        
        // Only start if session is not already running
        try {
            val isRunning = captureSession?.isRunning() ?: false
            if (!isRunning) {
                captureSession?.startRunning()
            }
        } catch (e: Exception) {
            // Silently fail on simulator or if camera is not available
            e.printStackTrace()
        }
    }
    
    fun stopCameraPreview() {
        // Only stop if session is running
        try {
            val isRunning = captureSession?.isRunning() ?: false
            if (isRunning) {
                captureSession?.stopRunning()
            }
        } catch (e: Exception) {
            // Silently handle stop errors
            e.printStackTrace()
        }
    }
    
    fun capturePhoto(callback: (ByteArray?) -> Unit) {
        val output = captureOutput ?: run {
            callback(null)
            return
        }
        
        // Create photo settings
        val settings = AVCapturePhotoSettings()
        
        // Store delegate to prevent garbage collection
        photoDelegate = PhotoCaptureDelegate(callback)
        output.capturePhotoWithSettings(settings, photoDelegate!!)
    }
    
    @OptIn(ExperimentalForeignApi::class)
    fun updatePreviewFrame(bounds: CValue<CGRect>) {
        // Update preview layer to match view bounds
        // Extract values from CValue<CGRect> and set layer bounds and position
        bounds.useContents {
            previewLayer?.let { layer ->
                val calayer = layer as CALayer
                // Create bounds using CGRectMake (x, y, width, height)
                val newBounds = CGRectMake(0.0, 0.0, size.width, size.height)
                calayer.bounds = newBounds
                // Set position to center of view bounds using CGPointMake
                val centerX = origin.x + size.width / 2.0
                val centerY = origin.y + size.height / 2.0
                calayer.position = CGPointMake(centerX, centerY)
            }
        }
    }
    
    fun release() {
        stopCameraPreview()
        previewLayer?.removeFromSuperlayer()
        captureSession?.inputs?.forEach { input ->
            captureSession?.removeInput(input as AVCaptureInput)
        }
        captureSession?.outputs?.forEach { output ->
            captureSession?.removeOutput(output as AVCaptureOutput)
        }
        captureSession = null
        captureDevice = null
        captureInput = null
        captureOutput = null
        previewLayer = null
        previewView = null
        photoDelegate = null
    }
}

@OptIn(ExperimentalForeignApi::class)
private class PhotoCaptureDelegate(
    private val callback: (ByteArray?) -> Unit
) : NSObject(), AVCapturePhotoCaptureDelegateProtocol {

    override fun captureOutput(
        output: AVCapturePhotoOutput,
        didFinishProcessingPhoto: AVCapturePhoto,
        error: NSError?
    ) {
        if (error != null) {
            callback(null)
            return
        }

        val imageData = didFinishProcessingPhoto.fileDataRepresentation()
        if (imageData != null) {
            val length = imageData.length.toInt()
            val bytes = ByteArray(length)
            bytes.usePinned {
                imageData.getBytes(it.addressOf(0), imageData.length)
            }
            callback(bytes)
        } else {
            callback(null)
        }
    }
}

actual class CameraController internal constructor(
    private val iosController: IOSCameraController
) {
    actual fun startCameraPreview() {
        iosController.startCameraPreview()
    }
    
    actual fun stopCameraPreview() {
        iosController.stopCameraPreview()
    }
    
    actual fun capturePhoto(callback: (ByteArray?) -> Unit) {
        iosController.capturePhoto(callback)
    }
    
    actual fun release() {
        iosController.release()
    }
    
    /**
     * Updates the preview layer frame to match the view bounds
     */
    @OptIn(ExperimentalForeignApi::class)
    fun updatePreviewFrame(bounds: CValue<CGRect>) {
        iosController.updatePreviewFrame(bounds)
    }
    
    companion object {
        fun create(previewView: UIView): CameraController {
            val iosController = IOSCameraController()
            iosController.setupCamera(previewView)
            return CameraController(iosController)
        }
    }
}

