package com.dev.devmath.core.ui

import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image

/**
 * Extension function to convert ByteArray to ImageBitmap
 * Supports JPEG, PNG, and other image formats
 */
fun ByteArray.toImageBitmap(): ImageBitmap? {
    return try {
        // Decode image from bytes using Skia
        val skiaImage = Image.makeFromEncoded(this) ?: return null
        val bitmap = Bitmap()
        bitmap.as
    } catch (e: Exception) {
        null
    }
}
