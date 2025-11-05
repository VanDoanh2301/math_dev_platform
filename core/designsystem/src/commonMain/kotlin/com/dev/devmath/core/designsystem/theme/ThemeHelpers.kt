/*
 * Copyright 2025 Mifos Initiative
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * See See https://github.com/openMF/kmp-project-template/blob/main/LICENSE
 */
package com.dev.devmath.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.core.KptThemeProvider


/**
 * Helper function to apply font family to all typography styles
 */
fun KptTypographyBuilder.applyFontFamily(fontFamily: FontFamily) {
    displayLarge = displayLarge.copy(fontFamily = fontFamily)
    displayMedium = displayMedium.copy(fontFamily = fontFamily)
    displaySmall = displaySmall.copy(fontFamily = fontFamily)
    headlineLarge = headlineLarge.copy(fontFamily = fontFamily)
    headlineMedium = headlineMedium.copy(fontFamily = fontFamily)
    headlineSmall = headlineSmall.copy(fontFamily = fontFamily)
    titleLarge = titleLarge.copy(fontFamily = fontFamily)
    titleMedium = titleMedium.copy(fontFamily = fontFamily)
    titleSmall = titleSmall.copy(fontFamily = fontFamily)
    bodyLarge = bodyLarge.copy(fontFamily = fontFamily)
    bodyMedium = bodyMedium.copy(fontFamily = fontFamily)
    bodySmall = bodySmall.copy(fontFamily = fontFamily)
    labelLarge = labelLarge.copy(fontFamily = fontFamily)
    labelMedium = labelMedium.copy(fontFamily = fontFamily)
    labelSmall = labelSmall.copy(fontFamily = fontFamily)
}

/**
 * Helper object providing convenient access to theme-related utilities
 * for easier theme development and customization.
 */
object ThemeHelpers {
    /**
     * Creates a complete theme provider with custom font family.
     * Useful when you want to apply a custom font across the entire theme.
     *
     * @param isDark Whether to use dark theme colors
     * @param customFontFamily Optional custom font family. If null, uses default Inter font.
     * @param block Optional DSL block for additional theme customizations
     * @return A configured KptThemeProvider
     */
    @Composable
    fun createThemeWithFont(
        isDark: Boolean = false,
        customFontFamily: FontFamily? = null,
        block: KptThemeBuilder.() -> Unit = {},
    ): KptThemeProvider {
        val font = customFontFamily ?: fontFamily
        return if (isDark) {
            kptDarkTheme {
                typography {
                    applyFontFamily(font)
                }
                block()
            }
        } else {
            kptLightTheme {
                typography {
                    applyFontFamily(font)
                }
                block()
            }
        }
    }
}

/**
 * Predefined theme configurations for common use cases.
 * These can be used as starting points for custom themes.
 */
object PredefinedThemes {
    /**
     * Default light theme with all standard configurations
     */
    val light: KptThemeProvider
        @Composable get() = createLightThemeProvider()

    /**
     * Default dark theme with all standard configurations
     */
    val dark: KptThemeProvider
        @Composable get() = createDarkThemeProvider()

    /**
     * Compact theme with reduced spacing for dense UIs
     */
    @Composable
    fun compact(isDark: Boolean = false): KptThemeProvider {
        return if (isDark) {
            kptDarkTheme {
                spacing {
                    xs = 2.dp
                    sm = 4.dp
                    md = 8.dp
                    lg = 12.dp
                    xl = 16.dp
                    xxl = 32.dp
                }
            }
        } else {
            kptLightTheme {
                spacing {
                    xs = 2.dp
                    sm = 4.dp
                    md = 8.dp
                    lg = 12.dp
                    xl = 16.dp
                    xxl = 32.dp
                }
            }
        }
    }

    /**
     * Comfortable theme with increased spacing for better readability
     */
    @Composable
    fun comfortable(isDark: Boolean = false): KptThemeProvider {
        return if (isDark) {
            kptDarkTheme {
                spacing {
                    xs = 6.dp
                    sm = 12.dp
                    md = 20.dp
                    lg = 32.dp
                    xl = 48.dp
                    xxl = 80.dp
                }
            }
        } else {
            kptLightTheme {
                spacing {
                    xs = 6.dp
                    sm = 12.dp
                    md = 20.dp
                    lg = 32.dp
                    xl = 48.dp
                    xxl = 80.dp
                }
            }
        }
    }
}

