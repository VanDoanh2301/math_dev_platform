package com.dev.devmath.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun MathView(
    modifier: Modifier = Modifier,
    text: String = "Math View",
    onViewCreated: ((Any?) -> Unit)? = null
)

