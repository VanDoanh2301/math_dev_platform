package com.dev.devmath.core.ui


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.skia.Image
import org.jetbrains.skia.Image.Companion

fun ByteArray.decodeToImageBitmap(): ImageBitmap {
    return this.decodeToImageBitmap()
}

fun ByteArray.decodeToPainter(): Painter {
    val imageBitmap = this.decodeToImageBitmap()
    return BitmapPainter(imageBitmap)
}


