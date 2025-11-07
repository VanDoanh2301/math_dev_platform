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

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.devmath.core.designsystem.core.ComponentDsl
import com.dev.devmath.core.designsystem.core.KptColorScheme
import com.dev.devmath.core.designsystem.core.KptCornerRadius
import com.dev.devmath.core.designsystem.core.KptElevation
import com.dev.devmath.core.designsystem.core.KptShapes
import com.dev.devmath.core.designsystem.core.KptSpacing
import com.dev.devmath.core.designsystem.core.KptThemeProvider
import com.dev.devmath.core.designsystem.core.KptTypography
import com.dev.devmath.core.designsystem.theme.primaryLight
import com.dev.devmath.core.designsystem.theme.onPrimaryLight
import com.dev.devmath.core.designsystem.theme.primaryContainerLight
import com.dev.devmath.core.designsystem.theme.onPrimaryContainerLight
import com.dev.devmath.core.designsystem.theme.secondaryLight
import com.dev.devmath.core.designsystem.theme.onSecondaryLight
import com.dev.devmath.core.designsystem.theme.secondaryContainerLight
import com.dev.devmath.core.designsystem.theme.onSecondaryContainerLight
import com.dev.devmath.core.designsystem.theme.tertiaryLight
import com.dev.devmath.core.designsystem.theme.onTertiaryLight
import com.dev.devmath.core.designsystem.theme.tertiaryContainerLight
import com.dev.devmath.core.designsystem.theme.onTertiaryContainerLight
import com.dev.devmath.core.designsystem.theme.errorLight
import com.dev.devmath.core.designsystem.theme.onErrorLight
import com.dev.devmath.core.designsystem.theme.errorContainerLight
import com.dev.devmath.core.designsystem.theme.onErrorContainerLight
import com.dev.devmath.core.designsystem.theme.backgroundLight
import com.dev.devmath.core.designsystem.theme.onBackgroundLight
import com.dev.devmath.core.designsystem.theme.surfaceLight
import com.dev.devmath.core.designsystem.theme.onSurfaceLight
import com.dev.devmath.core.designsystem.theme.surfaceVariantLight
import com.dev.devmath.core.designsystem.theme.onSurfaceVariantLight
import com.dev.devmath.core.designsystem.theme.outlineLight
import com.dev.devmath.core.designsystem.theme.outlineVariantLight
import com.dev.devmath.core.designsystem.theme.scrimLight
import com.dev.devmath.core.designsystem.theme.inverseSurfaceLight
import com.dev.devmath.core.designsystem.theme.inverseOnSurfaceLight
import com.dev.devmath.core.designsystem.theme.inversePrimaryLight
import com.dev.devmath.core.designsystem.theme.surfaceDimLight
import com.dev.devmath.core.designsystem.theme.surfaceBrightLight
import com.dev.devmath.core.designsystem.theme.surfaceContainerLowestLight
import com.dev.devmath.core.designsystem.theme.surfaceContainerLowLight
import com.dev.devmath.core.designsystem.theme.surfaceContainerLight
import com.dev.devmath.core.designsystem.theme.surfaceContainerHighLight
import com.dev.devmath.core.designsystem.theme.surfaceContainerHighestLight
import com.dev.devmath.core.designsystem.theme.primaryDark
import com.dev.devmath.core.designsystem.theme.onPrimaryDark
import com.dev.devmath.core.designsystem.theme.primaryContainerDark
import com.dev.devmath.core.designsystem.theme.onPrimaryContainerDark
import com.dev.devmath.core.designsystem.theme.secondaryDark
import com.dev.devmath.core.designsystem.theme.onSecondaryDark
import com.dev.devmath.core.designsystem.theme.secondaryContainerDark
import com.dev.devmath.core.designsystem.theme.onSecondaryContainerDark
import com.dev.devmath.core.designsystem.theme.tertiaryDark
import com.dev.devmath.core.designsystem.theme.onTertiaryDark
import com.dev.devmath.core.designsystem.theme.tertiaryContainerDark
import com.dev.devmath.core.designsystem.theme.onTertiaryContainerDark
import com.dev.devmath.core.designsystem.theme.errorDark
import com.dev.devmath.core.designsystem.theme.onErrorDark
import com.dev.devmath.core.designsystem.theme.errorContainerDark
import com.dev.devmath.core.designsystem.theme.onErrorContainerDark
import com.dev.devmath.core.designsystem.theme.backgroundDark
import com.dev.devmath.core.designsystem.theme.onBackgroundDark
import com.dev.devmath.core.designsystem.theme.surfaceDark
import com.dev.devmath.core.designsystem.theme.onSurfaceDark
import com.dev.devmath.core.designsystem.theme.surfaceVariantDark
import com.dev.devmath.core.designsystem.theme.onSurfaceVariantDark
import com.dev.devmath.core.designsystem.theme.outlineDark
import com.dev.devmath.core.designsystem.theme.outlineVariantDark
import com.dev.devmath.core.designsystem.theme.scrimDark
import com.dev.devmath.core.designsystem.theme.inverseSurfaceDark
import com.dev.devmath.core.designsystem.theme.inverseOnSurfaceDark
import com.dev.devmath.core.designsystem.theme.inversePrimaryDark
import com.dev.devmath.core.designsystem.theme.surfaceDimDark
import com.dev.devmath.core.designsystem.theme.surfaceBrightDark
import com.dev.devmath.core.designsystem.theme.surfaceContainerLowestDark
import com.dev.devmath.core.designsystem.theme.surfaceContainerLowDark
import com.dev.devmath.core.designsystem.theme.surfaceContainerDark
import com.dev.devmath.core.designsystem.theme.surfaceContainerHighDark
import com.dev.devmath.core.designsystem.theme.surfaceContainerHighestDark

/**
 * Light color scheme using colors from Color.kt
 */
@Immutable
data class KptColorSchemeImpl(
    override val primary: Color = primaryLight,
    override val onPrimary: Color = onPrimaryLight,
    override val primaryContainer: Color = primaryContainerLight,
    override val onPrimaryContainer: Color = onPrimaryContainerLight,
    override val secondary: Color = secondaryLight,
    override val onSecondary: Color = onSecondaryLight,
    override val secondaryContainer: Color = secondaryContainerLight,
    override val onSecondaryContainer: Color = onSecondaryContainerLight,
    override val tertiary: Color = tertiaryLight,
    override val onTertiary: Color = onTertiaryLight,
    override val tertiaryContainer: Color = tertiaryContainerLight,
    override val onTertiaryContainer: Color = onTertiaryContainerLight,
    override val error: Color = errorLight,
    override val onError: Color = onErrorLight,
    override val errorContainer: Color = errorContainerLight,
    override val onErrorContainer: Color = onErrorContainerLight,
    override val background: Color = backgroundLight,
    override val onBackground: Color = onBackgroundLight,
    override val surface: Color = surfaceLight,
    override val onSurface: Color = onSurfaceLight,
    override val surfaceVariant: Color = surfaceVariantLight,
    override val onSurfaceVariant: Color = onSurfaceVariantLight,
    override val outline: Color = outlineLight,
    override val outlineVariant: Color = outlineVariantLight,
    override val scrim: Color = scrimLight,
    override val inverseSurface: Color = inverseSurfaceLight,
    override val inverseOnSurface: Color = inverseOnSurfaceLight,
    override val inversePrimary: Color = inversePrimaryLight,
    override val surfaceDim: Color = surfaceDimLight,
    override val surfaceBright: Color = surfaceBrightLight,
    override val surfaceContainerLowest: Color = surfaceContainerLowestLight,
    override val surfaceContainerLow: Color = surfaceContainerLowLight,
    override val surfaceContainer: Color = surfaceContainerLight,
    override val surfaceContainerHigh: Color = surfaceContainerHighLight,
    override val surfaceContainerHighest: Color = surfaceContainerHighestLight,
    override val surfaceTint: Color = primaryLight,
) : KptColorScheme

/**
 * Dark color scheme using colors from Color.kt
 */
@Immutable
data class KptColorSchemeDarkImpl(
    override val primary: Color = primaryDark,
    override val onPrimary: Color = onPrimaryDark,
    override val primaryContainer: Color = primaryContainerDark,
    override val onPrimaryContainer: Color = onPrimaryContainerDark,
    override val secondary: Color = secondaryDark,
    override val onSecondary: Color = onSecondaryDark,
    override val secondaryContainer: Color = secondaryContainerDark,
    override val onSecondaryContainer: Color = onSecondaryContainerDark,
    override val tertiary: Color = tertiaryDark,
    override val onTertiary: Color = onTertiaryDark,
    override val tertiaryContainer: Color = tertiaryContainerDark,
    override val onTertiaryContainer: Color = onTertiaryContainerDark,
    override val error: Color = errorDark,
    override val onError: Color = onErrorDark,
    override val errorContainer: Color = errorContainerDark,
    override val onErrorContainer: Color = onErrorContainerDark,
    override val background: Color = backgroundDark,
    override val onBackground: Color = onBackgroundDark,
    override val surface: Color = surfaceDark,
    override val onSurface: Color = onSurfaceDark,
    override val surfaceVariant: Color = surfaceVariantDark,
    override val onSurfaceVariant: Color = onSurfaceVariantDark,
    override val outline: Color = outlineDark,
    override val outlineVariant: Color = outlineVariantDark,
    override val scrim: Color = scrimDark,
    override val inverseSurface: Color = inverseSurfaceDark,
    override val inverseOnSurface: Color = inverseOnSurfaceDark,
    override val inversePrimary: Color = inversePrimaryDark,
    override val surfaceDim: Color = surfaceDimDark,
    override val surfaceBright: Color = surfaceBrightDark,
    override val surfaceContainerLowest: Color = surfaceContainerLowestDark,
    override val surfaceContainerLow: Color = surfaceContainerLowDark,
    override val surfaceContainer: Color = surfaceContainerDark,
    override val surfaceContainerHigh: Color = surfaceContainerHighDark,
    override val surfaceContainerHighest: Color = surfaceContainerHighestDark,
    override val surfaceTint: Color = primaryDark,
) : KptColorScheme

@Immutable
data class KptTypographyImpl(
    override val displayLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    override val displayMedium: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    override val displaySmall: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    override val headlineLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    override val headlineMedium: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    override val headlineSmall: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    override val titleLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    override val titleMedium: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    override val titleSmall: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    override val bodyLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    override val bodyMedium: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    override val bodySmall: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
    override val labelLarge: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    override val labelMedium: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    override val labelSmall: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
) : KptTypography

@Immutable
data class KptShapesImpl(
    override val extraSmall: CornerBasedShape = RoundedCornerShape(4.dp),
    override val small: CornerBasedShape = RoundedCornerShape(8.dp),
    override val medium: CornerBasedShape = RoundedCornerShape(12.dp),
    override val large: CornerBasedShape = RoundedCornerShape(16.dp),
    override val extraLarge: CornerBasedShape = RoundedCornerShape(28.dp),
) : KptShapes

@Immutable
data class KptSpacingImpl(
    override val xs: Dp = 4.dp,
    override val sm: Dp = 8.dp,
    override val md: Dp = 16.dp,
    override val lg: Dp = 24.dp,
    override val xl: Dp = 32.dp,
    override val xxl: Dp = 64.dp,
) : KptSpacing

@Immutable
data class KptElevationImpl(
    override val level0: Dp = 0.dp,
    override val level1: Dp = 1.dp,
    override val level2: Dp = 3.dp,
    override val level3: Dp = 6.dp,
    override val level4: Dp = 8.dp,
    override val level5: Dp = 12.dp,
) : KptElevation

@Immutable
data class KptCornerRadiusImpl(
    override val extraSmall: Dp = 4.dp,
    override val small: Dp = 8.dp,
    override val medium: Dp = 12.dp,
    override val large: Dp = 16.dp,
    override val extraLarge: Dp = 28.dp
) : KptCornerRadius


@Immutable
data class KptThemeProviderImpl(
    override val colors: KptColorScheme = KptColorSchemeImpl(),
    override val typography: KptTypography = KptTypographyImpl(),
    override val shapes: KptShapes = KptShapesImpl(),
    override val spacing: KptSpacing = KptSpacingImpl(),
    override val elevation: KptElevation = KptElevationImpl(),
) : KptThemeProvider

/**
 * Creates a light theme provider with default values
 */
fun createLightThemeProvider(
    typography: KptTypography = KptTypographyImpl(),
    shapes: KptShapes = KptShapesImpl(),
    spacing: KptSpacing = KptSpacingImpl(),
    elevation: KptElevation = KptElevationImpl(),
): KptThemeProvider = KptThemeProviderImpl(
    colors = KptColorSchemeImpl(),
    typography = typography,
    shapes = shapes,
    spacing = spacing,
    elevation = elevation,
)

/**
 * Creates a dark theme provider with default values
 */
fun createDarkThemeProvider(
    typography: KptTypography = KptTypographyImpl(),
    shapes: KptShapes = KptShapesImpl(),
    spacing: KptSpacing = KptSpacingImpl(),
    elevation: KptElevation = KptElevationImpl(),
): KptThemeProvider = KptThemeProviderImpl(
    colors = KptColorSchemeDarkImpl(),
    typography = typography,
    shapes = shapes,
    spacing = spacing,
    elevation = elevation,
)

val LocalKptColors = staticCompositionLocalOf<KptColorScheme> { KptColorSchemeImpl() }
val LocalKptTypography = staticCompositionLocalOf<KptTypography> { KptTypographyImpl() }
val LocalKptShapes = staticCompositionLocalOf<KptShapes> { KptShapesImpl() }
val LocalKptSpacing = staticCompositionLocalOf<KptSpacing> { KptSpacingImpl() }
val LocalKptElevation = staticCompositionLocalOf<KptElevation> { KptElevationImpl() }
val LocalKptCornerRadius = staticCompositionLocalOf<KptCornerRadius> { KptCornerRadiusImpl() }



@ComponentDsl
class KptThemeBuilder {
    internal var colors: KptColorScheme = KptColorSchemeImpl()
    private var typography: KptTypography = KptTypographyImpl()
    private var shapes: KptShapes = KptShapesImpl()
    private var spacing: KptSpacing = KptSpacingImpl()
    private var elevation: KptElevation = KptElevationImpl()

    fun colors(block: KptColorSchemeBuilder.() -> Unit) {
        colors = KptColorSchemeBuilder().apply(block).build()
    }

    fun typography(block: KptTypographyBuilder.() -> Unit) {
        typography = KptTypographyBuilder().apply(block).build()
    }

    fun shapes(block: KptShapesBuilder.() -> Unit) {
        shapes = KptShapesBuilder().apply(block).build()
    }

    fun spacing(block: KptSpacingBuilder.() -> Unit) {
        spacing = KptSpacingBuilder().apply(block).build()
    }

    fun elevation(block: KptElevationBuilder.() -> Unit) {
        elevation = KptElevationBuilder().apply(block).build()
    }

    fun build(): KptThemeProvider = KptThemeProviderImpl(
        colors = colors,
        typography = typography,
        shapes = shapes,
        spacing = spacing,
        elevation = elevation,
    )
}

@ComponentDsl
class KptColorSchemeBuilder {
    var primary: Color = primaryLight
    var onPrimary: Color = onPrimaryLight
    var primaryContainer: Color = primaryContainerLight
    var onPrimaryContainer: Color = onPrimaryContainerLight
    var secondary: Color = secondaryLight
    var onSecondary: Color = onSecondaryLight
    var secondaryContainer: Color = secondaryContainerLight
    var onSecondaryContainer: Color = onSecondaryContainerLight
    var tertiary: Color = tertiaryLight
    var onTertiary: Color = onTertiaryLight
    var tertiaryContainer: Color = tertiaryContainerLight
    var onTertiaryContainer: Color = onTertiaryContainerLight
    var error: Color = errorLight
    var onError: Color = onErrorLight
    var errorContainer: Color = errorContainerLight
    var onErrorContainer: Color = onErrorContainerLight
    var background: Color = backgroundLight
    var onBackground: Color = onBackgroundLight
    var surface: Color = surfaceLight
    var onSurface: Color = onSurfaceLight
    var surfaceVariant: Color = surfaceVariantLight
    var onSurfaceVariant: Color = onSurfaceVariantLight
    var outline: Color = outlineLight
    var outlineVariant: Color = outlineVariantLight
    var scrim: Color = scrimLight
    var inverseSurface: Color = inverseSurfaceLight
    var inverseOnSurface: Color = inverseOnSurfaceLight
    var inversePrimary: Color = inversePrimaryLight
    var surfaceDim: Color = surfaceDimLight
    var surfaceBright: Color = surfaceBrightLight
    var surfaceContainerLowest: Color = surfaceContainerLowestLight
    var surfaceContainerLow: Color = surfaceContainerLowLight
    var surfaceContainer: Color = surfaceContainerLight
    var surfaceContainerHigh: Color = surfaceContainerHighLight
    var surfaceContainerHighest: Color = surfaceContainerHighestLight
    var surfaceTint: Color = primaryLight

    fun build(): KptColorScheme = KptColorSchemeImpl(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        inversePrimary = inversePrimary,
        surfaceDim = surfaceDim,
        surfaceBright = surfaceBright,
        surfaceContainerLowest = surfaceContainerLowest,
        surfaceContainerLow = surfaceContainerLow,
        surfaceContainer = surfaceContainer,
        surfaceContainerHigh = surfaceContainerHigh,
        surfaceContainerHighest = surfaceContainerHighest,
        surfaceTint = surfaceTint,
    )
    
    /**
     * Builds a dark color scheme instead of light
     */
    fun buildDark(): KptColorScheme = KptColorSchemeDarkImpl(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        outline = outline,
        outlineVariant = outlineVariant,
        scrim = scrim,
        inverseSurface = inverseSurface,
        inverseOnSurface = inverseOnSurface,
        inversePrimary = inversePrimary,
        surfaceDim = surfaceDim,
        surfaceBright = surfaceBright,
        surfaceContainerLowest = surfaceContainerLowest,
        surfaceContainerLow = surfaceContainerLow,
        surfaceContainer = surfaceContainer,
        surfaceContainerHigh = surfaceContainerHigh,
        surfaceContainerHighest = surfaceContainerHighest,
        surfaceTint = surfaceTint,
    )
}

@ComponentDsl
class KptTypographyBuilder {
    var displayLarge: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 57.sp)
    var displayMedium: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 45.sp)
    var displaySmall: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 36.sp)
    var headlineLarge: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 32.sp)
    var headlineMedium: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 28.sp)
    var headlineSmall: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 24.sp)
    var titleLarge: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 22.sp)
    var titleMedium: TextStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp)
    var titleSmall: TextStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp)
    var bodyLarge: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 16.sp)
    var bodyMedium: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)
    var bodySmall: TextStyle = TextStyle(fontWeight = FontWeight.Normal, fontSize = 12.sp)
    var labelLarge: TextStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 14.sp)
    var labelMedium: TextStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp)
    var labelSmall: TextStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 11.sp)

    fun build(): KptTypography = KptTypographyImpl(
        displayLarge = displayLarge,
        displayMedium = displayMedium,
        displaySmall = displaySmall,
        headlineLarge = headlineLarge,
        headlineMedium = headlineMedium,
        headlineSmall = headlineSmall,
        titleLarge = titleLarge,
        titleMedium = titleMedium,
        titleSmall = titleSmall,
        bodyLarge = bodyLarge,
        bodyMedium = bodyMedium,
        bodySmall = bodySmall,
        labelLarge = labelLarge,
        labelMedium = labelMedium,
        labelSmall = labelSmall,
    )
}

@ComponentDsl
class KptShapesBuilder {
    var extraSmall: CornerBasedShape = RoundedCornerShape(4.dp)
    var small: CornerBasedShape = RoundedCornerShape(8.dp)
    var medium: CornerBasedShape = RoundedCornerShape(12.dp)
    var large: CornerBasedShape = RoundedCornerShape(16.dp)
    var extraLarge: CornerBasedShape = RoundedCornerShape(28.dp)

    fun build(): KptShapes = KptShapesImpl(
        extraSmall = extraSmall,
        small = small,
        medium = medium,
        large = large,
        extraLarge = extraLarge,
    )
}

@ComponentDsl
class KptSpacingBuilder {
    var xs: Dp = 4.dp
    var sm: Dp = 8.dp
    var md: Dp = 16.dp
    var lg: Dp = 24.dp
    var xl: Dp = 32.dp
    var xxl: Dp = 64.dp

    fun build(): KptSpacing = KptSpacingImpl(
        xs = xs,
        sm = sm,
        md = md,
        lg = lg,
        xl = xl,
        xxl = xxl,
    )
}

@ComponentDsl
class KptElevationBuilder {
    var level0: Dp = 0.dp
    var level1: Dp = 1.dp
    var level2: Dp = 3.dp
    var level3: Dp = 6.dp
    var level4: Dp = 8.dp
    var level5: Dp = 12.dp

    fun build(): KptElevation = KptElevationImpl(
        level0 = level0,
        level1 = level1,
        level2 = level2,
        level3 = level3,
        level4 = level4,
        level5 = level5,
    )
}

object KptTheme {
    val colorScheme: KptColorScheme
        @Composable get() = LocalKptColors.current

    val typography: KptTypography
        @Composable get() = LocalKptTypography.current

    val shapes: KptShapes
        @Composable get() = LocalKptShapes.current

    val spacing: KptSpacing
        @Composable get() = LocalKptSpacing.current

    val elevation: KptElevation
        @Composable get() = LocalKptElevation.current

    val cornerRadius: KptCornerRadius
        @Composable get() = LocalKptCornerRadius.current


}

fun kptTheme(block: KptThemeBuilder.() -> Unit): KptThemeProvider {
    return KptThemeBuilder().apply(block).build()
}

/**
 * Creates a light theme using default colors from Color.kt
 */
fun kptLightTheme(block: KptThemeBuilder.() -> Unit = {}): KptThemeProvider {
    return KptThemeBuilder().apply {
        colors = KptColorSchemeImpl()
        block()
    }.build()
}

/**
 * Creates a dark theme using default colors from Color.kt
 */
fun kptDarkTheme(block: KptThemeBuilder.() -> Unit = {}): KptThemeProvider {
    return KptThemeBuilder().apply {
        colors = KptColorSchemeDarkImpl()
        block()
    }.build()
}
