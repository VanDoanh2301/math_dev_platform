package com.dev.devmath.core.feature.home.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.layout.KptSmoothLinearProgressIndicator
import com.dev.devmath.core.designsystem.theme.KptTheme
import com.dev.devmath.core.ui.resources.Res
import com.dev.devmath.core.ui.resources.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun SplashScreen() {
    KptMaterialTheme() {
        var showText by remember { mutableStateOf(false) }
        var progress by remember { mutableStateOf(0f) }

        LaunchedEffect(Unit) {
            delay(500)
            showText = true

            for (i in 0..100) {
                delay(20)
                progress = i / 100f
            }
        }

        val textAlpha by animateFloatAsState(
            targetValue = if (showText) 1f else 0f,
            animationSpec = tween(durationMillis = 1000),
            label = "textAlpha"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(Res.drawable.img_background_splash),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = KptTheme.spacing.lg)
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_logo_splash),
                    contentDescription = "App Logo",
                )

                Spacer(modifier = Modifier.height(KptTheme.spacing.md))

                Text(
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.alpha(textAlpha)
                )
            }

            KptSmoothLinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .padding(horizontal = KptTheme.spacing.lg, vertical = KptTheme.spacing.md)
                    .fillMaxWidth()
                    .height(KptTheme.spacing.sm)
                    .align(Alignment.BottomCenter)
                ,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                cornerRadius = KptTheme.cornerRadius.medium
            )
        }
    }
}

