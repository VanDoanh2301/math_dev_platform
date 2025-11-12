package com.dev.devmath.core.feature.home.math

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.icon.AppIcons
import com.dev.devmath.core.designsystem.theme.KptTheme
import com.dev.devmath.core.ui.MathView

/**
 * Math Solve screen with MathView
 */
@Composable
fun MathSolveScreen(
    onBackClick: () -> Unit
) {
    var mathText by remember { mutableStateOf("Math View") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // MathView
        MathView(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = KptTheme.spacing.xl,
                    bottom = KptTheme.spacing.xl,
                    start = KptTheme.spacing.md,
                    end = KptTheme.spacing.md
                ),
            text = mathText,
            onViewCreated = { view ->
                // View created callback - can be used to interact with the view
            }
        )
        
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(KptTheme.spacing.md)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = AppIcons.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

