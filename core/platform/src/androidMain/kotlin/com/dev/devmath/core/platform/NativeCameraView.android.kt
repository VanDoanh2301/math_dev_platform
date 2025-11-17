package com.dev.devmath.core.platform

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.dev.devmath.core.platform.camera.CameraScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
actual fun NativeCameraView(
    onImageCaptured: (ByteArray) -> Unit,
    onError: (Throwable) -> Unit, modifier: Modifier,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    CameraScreen(
       onImageCaptured = { uri ->
           scope.launch {
               try {
                   val byteArray = uri.toByteArray(context)
                   onImageCaptured(byteArray)
               } catch (e: Exception) {
                   onError(e)
               }
           }
       },
        onBack = onBackClick
    )
}

suspend fun Uri.toByteArray(context: Context): ByteArray = withContext(Dispatchers.IO) {
    context.contentResolver.openInputStream(this@toByteArray)?.use { stream ->
        stream.readBytes()
    } ?: throw IllegalStateException("Cannot read image from URI")
}

/**
 * Alternative: Non-suspend version
 */
fun Uri.toByteArraySync(context: Context): ByteArray {
    return context.contentResolver.openInputStream(this)?.use { stream ->
        stream.readBytes()
    } ?: throw IllegalStateException("Cannot read image from URI")
}