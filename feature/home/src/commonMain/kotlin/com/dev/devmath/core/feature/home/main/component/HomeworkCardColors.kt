package com.dev.devmath.core.feature.home.main.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Helper functions to get gradient colors from Material3 theme for homework cards
 */

/**
 * Gets gradient colors for Math Solve card
 * Uses primary and primaryContainer colors
 */
@Composable
fun getMathGradientColors(): List<Color> {
    return listOf(
        Color(0xFF3171F3),
        Color(0xFF6FB1FC),
    )
}

/**
 * Gets text color for Math Solve card
 */
@Composable
fun getMathTextColor(): Color {
    return MaterialTheme.colorScheme.onPrimary
}

/**
 * Gets gradient colors for Chemistry Solve card
 * Uses error and errorContainer colors
 */
@Composable
fun getChemistryGradientColors(): List<Color> {
    return listOf(
        Color(0xFFEB3349),
        Color(0xFFF45C43),
    )
}

/**
 * Gets text color for Chemistry Solve card
 */
@Composable
fun getChemistryTextColor(): Color {
    return MaterialTheme.colorScheme.onError
}

/**
 * Gets gradient colors for Physic Solve card
 * Uses tertiary and tertiaryContainer colors
 */
@Composable
fun getPhysicGradientColors(): List<Color> {
    return listOf(
        Color(0xFF48B1BF),
        Color(0xFF06BEB6),
    )
}

/**
 * Gets text color for Physic Solve card
 */
@Composable
fun getPhysicTextColor(): Color {
    return MaterialTheme.colorScheme.onTertiary
}

/**
 * Gets gradient colors for Translate card
 * Uses primary and secondary colors
 */
@Composable
fun getTranslateGradientColors(): List<Color> {
    return listOf(
        Color(0xFFF46B45),
        Color(0xFFEEA849),
    )
}

/**
 * Gets text color for Translate card
 */
@Composable
fun getTranslateTextColor(): Color {
    return MaterialTheme.colorScheme.onPrimary
}


@Composable
fun getPremiumGradientColors(): List<Color> {
    return listOf(
        Color(0xFFFF8902),
        Color(0xFFFE420D),
    )
}
