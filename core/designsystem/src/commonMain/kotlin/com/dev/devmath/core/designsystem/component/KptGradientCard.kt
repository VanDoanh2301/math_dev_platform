/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See See https://github.com/openMF/kmp-project-template/blob/main/LICENSE
 */
package com.dev.devmath.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.theme.KptTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Gradient Card component with Material3 integration
 *
 * @param gradientColors List of colors for the gradient background
 * @param onClick Callback when card is clicked
 * @param modifier Modifier to be applied to the card
 * @param shape Shape of the card. Defaults to KptTheme.shapes.large
 * @param elevation Elevation in dp. Defaults to KptTheme.elevation.level1
 * @param enabled Whether the card is enabled
 * @param contentPadding Padding for card content
 * @param interactionSource MutableInteractionSource for tracking interactions
 * @param content Card content
 */
@Composable
fun KptGradientCard(
    gradientColors: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = KptTheme.shapes.large,
    elevation: Dp = KptTheme.elevation.level1,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(KptTheme.spacing.md),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Surface(
        onClick = if (enabled) onClick else ({}),
        modifier = modifier
            .shadow(
                elevation = elevation,
                shape = shape
            ),
        shape = shape,
        enabled = enabled,
        interactionSource = interactionSource,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = gradientColors
                    ),
                    shape = shape
                )
                .padding(contentPadding)
        ) {
            content()
        }
    }
}

@Preview(name = "KptGradientCard - Light Theme")
@Composable
fun KptGradientCardLightPreview() {
    KptMaterialTheme(darkTheme = false) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            KptGradientCard(
                gradientColors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {},
                modifier = Modifier
                    .fillMaxSize()
                    .height(120.dp)
            ) {

            }
        }
    }
}

@Preview(name = "KptGradientCard - Dark Theme")
@Composable
fun KptGradientCardDarkPreview() {
    KptMaterialTheme(darkTheme = true) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            KptGradientCard(
                gradientColors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {},
                modifier = Modifier
                    .fillMaxSize()
                    .height(120.dp)
            ) {
                // Preview content
            }
        }
    }
}

