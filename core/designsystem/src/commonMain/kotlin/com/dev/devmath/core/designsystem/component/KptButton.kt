/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.dev.devmath.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.theme.KptTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Variant for KptButton
 */
enum class KptButtonVariant {
    /** Filled button with background color */
    Filled,
    /** Outlined button with border stroke */
    Outlined,
    /** Text button with no background */
    Text
}

/**
 * KptButton - Custom button component with support for multiple variants
 *
 * @param onClick Callback when button is clicked
 * @param modifier Modifier to be applied to the button
 * @param variant Button variant (Filled, Outlined, or Text). Defaults to Filled
 * @param enabled Whether the button is enabled
 * @param shape Shape of the button
 * @param colors Custom colors for the button
 * @param borderStroke Border stroke for outlined variant
 * @param contentPadding Padding for button content
 * @param interactionSource MutableInteractionSource for tracking interactions
 * @param content Button content (typically Text)
 */
@Composable
fun KptButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: KptButtonVariant = KptButtonVariant.Filled,
    enabled: Boolean = true,
    shape: Shape? = null,
    colors: ButtonColors? = null,
    borderStroke: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val defaultShape = shape ?: KptTheme.shapes.small
    val defaultColors = colors ?: when (variant) {
        KptButtonVariant.Filled -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
        KptButtonVariant.Outlined -> ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
        KptButtonVariant.Text -> ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    val defaultBorderStroke = borderStroke ?: BorderStroke(
        width = 1.dp,
        color = if (enabled) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.outlineVariant
    )

    when (variant) {
        KptButtonVariant.Filled -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                shape = defaultShape,
                colors = defaultColors,
                contentPadding = contentPadding,
                interactionSource = interactionSource,
                content = content
            )
        }
        KptButtonVariant.Outlined -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                shape = defaultShape,
                colors = defaultColors,
                border = defaultBorderStroke,
                contentPadding = contentPadding,
                interactionSource = interactionSource,
                content = content
            )
        }
        KptButtonVariant.Text -> {
            TextButton(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
                shape = defaultShape,
                colors = defaultColors,
                contentPadding = contentPadding,
                interactionSource = interactionSource,
                content = content
            )
        }
    }
}

/**
 * Convenience function for filled button with default spacing
 */
@Composable
fun KptFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = KptTheme.spacing.lg,
        vertical = KptTheme.spacing.sm
    ),
    content: @Composable RowScope.() -> Unit
) {
    KptButton(
        onClick = onClick,
        modifier = modifier,
        variant = KptButtonVariant.Filled,
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding,
        content = content
    )
}

/**
 * Convenience function for outlined button with default spacing
 */
@Composable
fun KptOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    borderStroke: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = KptTheme.spacing.lg,
        vertical = KptTheme.spacing.sm
    ),
    content: @Composable RowScope.() -> Unit
) {
    KptButton(
        onClick = onClick,
        modifier = modifier,
        variant = KptButtonVariant.Outlined,
        enabled = enabled,
        shape = shape,
        borderStroke = borderStroke,
        contentPadding = contentPadding,
        content = content
    )
}

/**
 * Convenience function for text button with default spacing
 */
@Composable
fun KptTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = KptTheme.spacing.lg,
        vertical = KptTheme.spacing.sm
    ),
    content: @Composable RowScope.() -> Unit
) {
    KptButton(
        onClick = onClick,
        modifier = modifier,
        variant = KptButtonVariant.Text,
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding,
        content = content
    )
}

@Preview(name = "KptButton - Light Theme")
@Composable
fun KptButtonLightPreview() {
    KptMaterialTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            KptFilledButton(onClick = {}) {
                Text("Filled Button")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptOutlinedButton(onClick = {}) {
                Text("Outlined Button")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptTextButton(onClick = {}) {
                Text("Text Button")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptFilledButton(onClick = {}, enabled = false) {
                Text("Disabled Filled")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptOutlinedButton(onClick = {}, enabled = false) {
                Text("Disabled Outlined")
            }
        }
    }
}

@Preview(name = "KptButton - Dark Theme")
@Composable
fun KptButtonDarkPreview() {
    KptMaterialTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            KptFilledButton(onClick = {}) {
                Text("Filled Button")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptOutlinedButton(onClick = {}) {
                Text("Outlined Button")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptTextButton(onClick = {}) {
                Text("Text Button")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptFilledButton(onClick = {}, enabled = false) {
                Text("Disabled Filled")
            }
            Spacer(modifier = Modifier.height(8.dp))
            KptOutlinedButton(onClick = {}, enabled = false) {
                Text("Disabled Outlined")
            }
        }
    }
}
