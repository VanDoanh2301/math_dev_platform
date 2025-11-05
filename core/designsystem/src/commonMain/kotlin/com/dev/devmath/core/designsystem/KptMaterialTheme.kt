/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See See https://github.com/openMF/kmp-project-template/blob/main/LICENSE
 */
package com.dev.devmath.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.devmath.core.designsystem.core.KptThemeProvider
import com.dev.devmath.core.designsystem.theme.KptTheme
import com.dev.devmath.core.designsystem.theme.KptThemeProviderImpl
import com.dev.devmath.core.designsystem.theme.LocalKptColors
import com.dev.devmath.core.designsystem.theme.LocalKptElevation
import com.dev.devmath.core.designsystem.theme.LocalKptShapes
import com.dev.devmath.core.designsystem.theme.LocalKptSpacing
import com.dev.devmath.core.designsystem.theme.LocalKptTypography
import com.dev.devmath.core.designsystem.theme.applyFontFamily
import com.dev.devmath.core.designsystem.theme.createDarkThemeProvider
import com.dev.devmath.core.designsystem.theme.createLightThemeProvider
import com.dev.devmath.core.designsystem.theme.fontFamily
import com.dev.devmath.core.designsystem.theme.kptDarkTheme
import com.dev.devmath.core.designsystem.theme.kptLightTheme
import com.dev.devmath.core.designsystem.theme.kptTheme

/**
 * KptMaterialTheme provides Material3 integration for KptTheme.
 * This composable applies KptTheme values to MaterialTheme automatically,
 * making all Material3 components use KptTheme design tokens.
 *
 * @param theme KptThemeProvider instance containing design tokens
 * @param content The composable content that will have access to both KptTheme and MaterialTheme
 *
 * @sample KptMaterialThemeUsageExample
 */
@Composable
fun KptMaterialTheme(
    theme: KptThemeProvider = createLightThemeProvider(),
    content: @Composable () -> Unit,
) {
    // Convert KptTheme values to Material3 equivalents
    val materialColorScheme = theme.colors.toMaterial3ColorScheme()
    val materialTypography = theme.typography.toMaterial3Typography(fontFamily = fontFamily)
    val materialShapes = theme.shapes.toMaterial3Shapes()

    // Provide both KptTheme composition locals and MaterialTheme
    CompositionLocalProvider(
        LocalKptColors provides theme.colors,
        LocalKptTypography provides theme.typography,
        LocalKptShapes provides theme.shapes,
        LocalKptSpacing provides theme.spacing,
        LocalKptElevation provides theme.elevation,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = materialTypography,
            shapes = materialShapes,
            content = content,
        )
    }

}

/**
 * KptMaterialTheme with dark theme support.
 * Provides automatic light/dark theme switching with Material3 integration.
 *
 * @param darkTheme Whether to use dark theme. Defaults to system preference.
 * @param lightTheme KptThemeProvider for light theme
 * @param darkTheme KptThemeProvider for dark theme
 * @param content The composable content that will have access to both KptTheme and MaterialTheme
 *
 * @sample KptMaterialThemeWithDarkModeExample
 */
@Composable
fun KptMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    lightTheme: KptThemeProvider = createLightThemeProvider(),
    darkThemeProvider: KptThemeProvider = createDarkThemeProvider(),
    content: @Composable () -> Unit,
) {
    val selectedTheme = if (darkTheme) darkThemeProvider else lightTheme
    KptMaterialTheme(
        theme = selectedTheme,
        content = content,
    )
}

/**
 * DSL builder for creating KptMaterialTheme with custom configuration
 */
@Composable
fun KptMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeBuilder: @Composable (Boolean) -> KptThemeProvider,
    content: @Composable () -> Unit,
) {
    val theme = themeBuilder(darkTheme)
    KptMaterialTheme(
        theme = theme,
        content = content,
    )
}

// region Usage Examples (for documentation)

/**
 * Example of basic KptMaterialTheme usage
 */
@Composable
private fun KptMaterialThemeUsageExample() {
    KptMaterialTheme {
        // All Material3 components will use KptTheme values
        MaterialTheme.colorScheme.primary // = KptTheme.colorScheme.primary
        MaterialTheme.typography.titleLarge // = KptTheme.typography.titleLarge
        MaterialTheme.shapes.medium // = KptTheme.shapes.medium

        // KptTheme values are also available directly
        KptTheme.spacing.md
        KptTheme.elevation.level2
    }
}

/**
 * Example of KptMaterialTheme with dark mode support
 */
@Composable
private fun KptMaterialThemeWithDarkModeExample() {
    // Using default light and dark themes
    KptMaterialTheme {
        // Theme automatically switches based on system preference
        // Material3 components inherit the appropriate theme
    }
    
    // Or with custom themes
    val lightTheme = kptLightTheme {
        typography {
            titleLarge = titleLarge.copy(fontSize = 24.sp)
        }
    }

    val darkTheme = kptDarkTheme {
        typography {
            titleLarge = titleLarge.copy(fontSize = 24.sp)
        }
    }

    KptMaterialTheme(
        lightTheme = lightTheme,
        darkThemeProvider = darkTheme,
    ) {
        // Custom themes with automatic dark mode switching
    }
}

/**
 * Example of KptMaterialTheme with DSL builder
 */
@Composable
private fun KptMaterialThemeBuilderExample() {
    KptMaterialTheme(
        themeBuilder = { isDark ->
            val font =  fontFamily
            if (isDark) {
                kptDarkTheme {
                    typography {
                        applyFontFamily(font)
                    }
                    shapes {
                        medium = Shapes().medium.copy(all = CornerSize(16.dp))
                    }
                }
            } else {
                kptLightTheme {
                    typography {
                        applyFontFamily(font)
                    }
                    shapes {
                        medium = Shapes().medium.copy(all = CornerSize(16.dp))
                    }
                }
            }
        },
    ) {
        // Dynamic theme based on dark mode with customizations
    }
}

// endregion
