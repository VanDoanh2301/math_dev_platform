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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.dev.devmath.core.designsystem.core.KptThemeProvider
import com.dev.devmath.core.designsystem.theme.createLightThemeProvider
import com.dev.devmath.core.designsystem.theme.LocalKptColors
import com.dev.devmath.core.designsystem.theme.LocalKptElevation
import com.dev.devmath.core.designsystem.theme.LocalKptShapes
import com.dev.devmath.core.designsystem.theme.LocalKptSpacing
import com.dev.devmath.core.designsystem.theme.LocalKptTypography

/**
 * KptTheme provides the core theming composable that makes all KPT design tokens
 * available to child components through Composition Locals.
 *
 * This composable sets up the theme hierarchy by providing:
 * - [LocalKptColors] for color design tokens
 * - [LocalKptTypography] for typography design tokens
 * - [LocalKptShapes] for shape design tokens
 * - [LocalKptSpacing] for spacing design tokens
 * - [LocalKptElevation] for elevation design tokens
 *
 * Usage:
 * ```
 * KptTheme {
 *     // All child components can now access:
 *     // KptTheme.colorScheme
 *     // KptTheme.typography
 *     // KptTheme.shapes
 *     // KptTheme.spacing
 *     // KptTheme.elevation
 *     MyScreen()
 * }
 * ```
 *
 * For Material3 integration, consider using [com.dev.devmath.core.designsystem.KptMaterialTheme] instead,
 * which provides both KPT design tokens and Material3 theme compatibility.
 *
 * @param theme The theme provider containing all design tokens. Defaults to [KptThemeProviderImpl]
 * @param content The composable content that will have access to the theme
 *
 * @see com.dev.devmath.core.designsystem.KptMaterialTheme
 * @see KptThemeProvider
 */
@Composable
fun KptTheme(
    theme: KptThemeProvider = createLightThemeProvider(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalKptColors provides theme.colors,
        LocalKptTypography provides theme.typography,
        LocalKptShapes provides theme.shapes,
        LocalKptSpacing provides theme.spacing,
        LocalKptElevation provides theme.elevation,
    ) {
        content()
    }
}
