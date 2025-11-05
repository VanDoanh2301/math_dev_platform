/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.dev.devmath.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.theme.KptTheme

/**
 * Variant for KptTextField
 */
enum class KptTextFieldVariant {
    /** Filled text field with background */
    Filled,
    /** Outlined text field with border stroke */
    Outlined
}

/**
 * KptTextField - Custom text field component with support for multiple variants
 *
 * @param value The text to be displayed
 * @param onValueChange Callback when text changes
 * @param modifier Modifier to be applied to the text field
 * @param variant Text field variant (Filled or Outlined). Defaults to Outlined
 * @param enabled Whether the text field is enabled
 * @param readOnly Whether the text field is read-only
 * @param label Label to be displayed
 * @param placeholder Placeholder text
 * @param leadingIcon Leading icon
 * @param trailingIcon Trailing icon
 * @param supportingText Supporting text
 * @param isError Whether the text field has error state
 * @param visualTransformation Visual transformation for the text
 * @param keyboardOptions Keyboard options
 * @param keyboardActions Keyboard actions
 * @param singleLine Whether the text field is single line
 * @param maxLines Maximum number of lines
 * @param minLines Minimum number of lines
 * @param shape Shape of the text field
 * @param colors Custom colors for the text field
 * @param textStyle Text style for the input text
 * @param interactionSource MutableInteractionSource for tracking interactions
 */
@Composable
fun KptTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    variant: KptTextFieldVariant = KptTextFieldVariant.Outlined,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default,
    keyboardActions: androidx.compose.foundation.text.KeyboardActions = androidx.compose.foundation.text.KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    shape: Shape? = null,
    colors: TextFieldColors? = null,
    textStyle: TextStyle = KptTheme.typography.bodyLarge,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val defaultShape = shape ?: when (variant) {
        KptTextFieldVariant.Filled -> KptTheme.shapes.small
        KptTextFieldVariant.Outlined -> KptTheme.shapes.small
    }

    val defaultColors = colors ?: when (variant) {
        KptTextFieldVariant.Filled -> TextFieldDefaults.colors(
            focusedContainerColor = KptTheme.colorScheme.surfaceContainerHighest,
            unfocusedContainerColor = KptTheme.colorScheme.surfaceContainerHigh,
            disabledContainerColor = KptTheme.colorScheme.surfaceContainerLow,
            errorContainerColor = KptTheme.colorScheme.errorContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedLabelColor = KptTheme.colorScheme.primary,
            unfocusedLabelColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorLabelColor = KptTheme.colorScheme.error,
            focusedPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant,
            focusedTextColor = KptTheme.colorScheme.onSurface,
            unfocusedTextColor = KptTheme.colorScheme.onSurface,
            disabledTextColor = KptTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorTextColor = KptTheme.colorScheme.onErrorContainer,
            focusedLeadingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorLeadingIconColor = KptTheme.colorScheme.error,
            focusedTrailingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorTrailingIconColor = KptTheme.colorScheme.error,
            focusedSupportingTextColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedSupportingTextColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorSupportingTextColor = KptTheme.colorScheme.error
        )
        KptTextFieldVariant.Outlined -> OutlinedTextFieldDefaults.colors(
            focusedBorderColor = KptTheme.colorScheme.primary,
            unfocusedBorderColor = KptTheme.colorScheme.outline,
            disabledBorderColor = KptTheme.colorScheme.outlineVariant.copy(alpha = 0.38f),
            errorBorderColor = KptTheme.colorScheme.error,
            focusedLabelColor = KptTheme.colorScheme.primary,
            unfocusedLabelColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorLabelColor = KptTheme.colorScheme.error,
            focusedPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorPlaceholderColor = KptTheme.colorScheme.onSurfaceVariant,
            focusedTextColor = KptTheme.colorScheme.onSurface,
            unfocusedTextColor = KptTheme.colorScheme.onSurface,
            disabledTextColor = KptTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorTextColor = KptTheme.colorScheme.onErrorContainer,
            focusedLeadingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorLeadingIconColor = KptTheme.colorScheme.error,
            focusedTrailingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorTrailingIconColor = KptTheme.colorScheme.error,
            focusedSupportingTextColor = KptTheme.colorScheme.onSurfaceVariant,
            unfocusedSupportingTextColor = KptTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = KptTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f),
            errorSupportingTextColor = KptTheme.colorScheme.error
        )
    }

    when (variant) {
        KptTextFieldVariant.Filled -> {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled,
                readOnly = readOnly,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                supportingText = supportingText,
                isError = isError,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                shape = defaultShape,
                colors = defaultColors,
                textStyle = textStyle,
                interactionSource = interactionSource
            )
        }
        KptTextFieldVariant.Outlined -> {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                enabled = enabled,
                readOnly = readOnly,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                supportingText = supportingText,
                isError = isError,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                shape = defaultShape,
                colors = defaultColors,
                textStyle = textStyle,
                interactionSource = interactionSource
            )
        }
    }
}

/**
 * Convenience function for filled text field
 */
@Composable
fun KptFilledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default,
    keyboardActions: androidx.compose.foundation.text.KeyboardActions = androidx.compose.foundation.text.KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1
) {
    KptTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        variant = KptTextFieldVariant.Filled,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines
    )
}

/**
 * Convenience function for outlined text field
 */
@Composable
fun KptOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default,
    keyboardActions: androidx.compose.foundation.text.KeyboardActions = androidx.compose.foundation.text.KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1
) {
    KptTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        variant = KptTextFieldVariant.Outlined,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines
    )
}

