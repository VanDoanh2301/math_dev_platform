package com.dev.devmath.core.platform.camera

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.dev.devmath.core.designsystem.theme.KptTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    onImageCaptured: (Uri) -> Unit,
    onBack: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    when {
        cameraPermissionState.status.isGranted -> {
            CameraContent(
                onImageCaptured = onImageCaptured,
                onBack = onBack
            )
        }
        else -> {
            PermissionDeniedScreen(onBack = onBack)
        }
    }
}

@Composable
private fun CameraContent(
    onImageCaptured: (Uri) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var flashMode by remember { mutableIntStateOf(ImageCapture.FLASH_MODE_OFF) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var previewView by remember { mutableStateOf<PreviewView?>(null) }

    val executor = remember { ContextCompat.getMainExecutor(context) }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { launchUCrop(context, it, onImageCaptured) }
    }

    // UCrop result launcher
    val uCropLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.let { intent ->
                UCrop.getOutput(intent)?.let { croppedUri ->
                    onImageCaptured(croppedUri)
                }
            }
        }
        capturedImageUri = null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        if (capturedImageUri == null) {
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        previewView = this
                        setupCamera(
                            context = ctx,
                            lifecycleOwner = lifecycleOwner,
                            previewView = this,
                            onImageCaptureReady = { capture ->
                                imageCapture = capture
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Show captured image preview
            Image(
                painter = rememberAsyncImagePainter(capturedImageUri),
                contentDescription = "Captured photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Back Button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(KptTheme.spacing.md)
                .background(Color.Black.copy(alpha = 0.4f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        // Bottom Controls
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 50.dp)
        ) {
            if (capturedImageUri == null) {
                // Capture Mode Controls
                CaptureControls(
                    flashMode = flashMode,
                    onFlashToggle = {
                        flashMode = when (flashMode) {
                            ImageCapture.FLASH_MODE_OFF -> ImageCapture.FLASH_MODE_ON
                            ImageCapture.FLASH_MODE_ON -> ImageCapture.FLASH_MODE_AUTO
                            else -> ImageCapture.FLASH_MODE_OFF
                        }
                        imageCapture?.flashMode = flashMode
                    },
                    onGalleryClick = { galleryLauncher.launch("image/*") },
                    onCaptureClick = {
                        takePicture(
                            context = context,
                            imageCapture = imageCapture,
                            executor = executor,
                            onImageCaptured = { uri ->
                                capturedImageUri = uri
                            }
                        )
                    }
                )
            } else {
                // Preview Mode Controls
                PreviewControls(
                    onRetake = { capturedImageUri = null },
                    onNext = {
                        capturedImageUri?.let { uri ->
                            launchUCrop(context, uri, onImageCaptured, uCropLauncher)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CaptureControls(
    flashMode: Int,
    onFlashToggle: () -> Unit,
    onGalleryClick: () -> Unit,
    onCaptureClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Gallery Button
        IconButton(
            onClick = onGalleryClick,
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = "Gallery",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        // Capture Button
        Box(
            modifier = Modifier
                .size(90.dp)
                .clickable(onClick = onCaptureClick),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }

        // Flash Button
        IconButton(
            onClick = onFlashToggle,
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = getFlashIcon(flashMode),
                contentDescription = "Flash",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun PreviewControls(
    onRetake: () -> Unit,
    onNext: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(KptTheme.spacing.md)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth().align(Alignment.TopEnd),

            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = onRetake,
                modifier = Modifier
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Retake",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        // Next button (bottom left)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            contentAlignment = Alignment.BottomStart
        ) {
            Button(
                onClick = onNext,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Next")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}



@Composable
private fun PermissionDeniedScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Camera permission required",
                color = Color.White.copy(alpha = 0.6f)
            )
        }

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}

// Helper Functions

private fun setupCamera(
    context: android.content.Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    previewView: PreviewView,
    onImageCaptureReady: (ImageCapture) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()

        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        val imageCapture = ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_OFF)
            .build()

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )

            onImageCaptureReady(imageCapture)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }, ContextCompat.getMainExecutor(context))
}

private fun takePicture(
    context: android.content.Context,
    imageCapture: ImageCapture?,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit
) {
    imageCapture ?: return

    val photoFile = File(
        context.cacheDir,
        SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US)
            .format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                onImageCaptured(Uri.fromFile(photoFile))
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }
        }
    )
}

private fun launchUCrop(
    context: android.content.Context,
    sourceUri: Uri,
    onImageCaptured: (Uri) -> Unit,
    launcher: androidx.activity.result.ActivityResultLauncher<android.content.Intent>? = null
) {
    val cacheDir = context.cacheDir
    val fileName =
        "cropped_${System.currentTimeMillis()}.jpg"

    val options = UCrop.Options().apply {
        setCompressionFormat(Bitmap.CompressFormat.PNG)
        setCompressionQuality(100)
        setFreeStyleCropEnabled(true)
        setStatusBarLight(true)
    }

    if (launcher != null) {
        launcher.launch(UCrop.of(sourceUri ,Uri.fromFile(File(cacheDir, fileName)))
            .withOptions(options)
            .getIntent(context))
    } else {
        onImageCaptured(sourceUri)
    }
}

private fun getFlashIcon(flashMode: Int): ImageVector {
    return when (flashMode) {
        ImageCapture.FLASH_MODE_ON -> Icons.Default.FlashOn
        ImageCapture.FLASH_MODE_AUTO -> Icons.Default.FlashAuto
        else -> Icons.Default.FlashOff
    }
}