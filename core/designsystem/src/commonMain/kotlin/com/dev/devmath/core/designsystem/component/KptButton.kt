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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.KptTheme
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
    colors: ButtonColors? = null,
    borderStroke: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val defaultColors = colors ?: when (variant) {
        KptButtonVariant.Filled -> ButtonDefaults.buttonColors(
            containerColor = KptTheme.colorScheme.primary,
            contentColor = KptTheme.colorScheme.onPrimary,
            disabledContainerColor = KptTheme.colorScheme.surfaceVariant,
            disabledContentColor = KptTheme.colorScheme.onSurfaceVariant
        )
        KptButtonVariant.Outlined -> ButtonDefaults.outlinedButtonColors(
            contentColor = KptTheme.colorScheme.primary,
            disabledContentColor = KptTheme.colorScheme.onSurfaceVariant
        )
        KptButtonVariant.Text -> ButtonDefaults.textButtonColors(
            contentColor = KptTheme.colorScheme.primary,
            disabledContentColor = KptTheme.colorScheme.onSurfaceVariant
        )
    }

    val defaultBorderStroke = borderStroke ?: BorderStroke(
        width = 1.dp,
        color = if (enabled) KptTheme.colorScheme.outline else KptTheme.colorScheme.outlineVariant
    )

    when (variant) {
        KptButtonVariant.Filled -> {
            Button(
                onClick = onClick,
                modifier = modifier,
                enabled = enabled,
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
        contentPadding = contentPadding,
        content = content
    )
}

@Preview
@Composable
fun KptButtonPreview() {
    KptMaterialTheme(
        darkTheme = false
    ) {
        Column {
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
        }
    }
}
