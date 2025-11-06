package com.dev.devmath.core.designsystem.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun KptSmoothLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    cornerRadius: Dp = 4.dp
) {
    Canvas(modifier = modifier.height(4.dp)) {
        val cornerRadiusPx = cornerRadius.toPx()

        drawRoundRect(
            color = trackColor,
            size = size,
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
        )

        val progressWidth = size.width * progress.coerceIn(0f, 1f)
        if (progressWidth > 0) {
            drawRoundRect(
                color = color,
                size = size.copy(width = progressWidth),
                cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
            )
        }
    }
}