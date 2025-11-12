package com.dev.devmath.core.feature.home.main.component

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.component.KptGradientCard
import com.dev.devmath.core.designsystem.theme.KptTheme
import com.dev.devmath.core.ui.resources.Res
import com.dev.devmath.core.ui.resources.ic_home_math
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Homework Card component with gradient background
 *
 * @param title Title text for the card
 * @param icon Icon composable for the card
 * @param gradientColors List of colors for the gradient background
 * @param onClick Callback when card is clicked
 * @param modifier Modifier to be applied to the card
 * @param textColor Color for the text. If null, uses appropriate color based on gradient
 */
@Composable
fun HomeworkCard(
    title: String,
    icon: @Composable () -> Unit,
    gradientColors: List<Color>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color? = null
) {
    // Determine text color - use onPrimary for most cases, or custom if provided
    val defaultTextColor = textColor ?: MaterialTheme.colorScheme.onPrimary
    
    KptGradientCard(
        gradientColors = gradientColors,
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.background(color = Color.Transparent).fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            
            Spacer(modifier = Modifier.height(KptTheme.spacing.md))
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = defaultTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(name = "HomeworkCard - Math")
@Composable
fun HomeworkCardMathPreview() {
    KptMaterialTheme(darkTheme = false) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            HomeworkCard(
                title = "Math Solve",
                icon = {
                    Image(painter = painterResource(Res.drawable.ic_home_math), contentDescription = null, modifier = Modifier.fillMaxSize())
                },
                gradientColors = getMathGradientColors(),
                onClick = {},
                modifier = Modifier.wrapContentHeight()
            )
        }
    }
}

