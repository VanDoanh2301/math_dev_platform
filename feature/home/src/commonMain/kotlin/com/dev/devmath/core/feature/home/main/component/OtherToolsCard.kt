package com.dev.devmath.core.feature.home.main.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptElevationDefaults
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.theme.KptTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Other Tools Card component with surface background
 *
 * @param title Title text for the card
 * @param icon Icon composable for the card
 * @param onClick Callback when card is clicked
 * @param modifier Modifier to be applied to the card
 * @param backgroundColor Background color for the card. Defaults to surfaceVariant
 * @param textColor Color for the text. Defaults to onSurfaceVariant
 */
@Composable
fun OtherToolsCard(
    title: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = KptTheme.shapes.large,
        interactionSource = interactionSource,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(KptTheme.spacing.md),
            horizontalArrangement = Arrangement.spacedBy(KptTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
        }
    }
}

@Preview(name = "OtherToolsCard - Light Theme")
@Composable
fun OtherToolsCardLightPreview() {
    KptMaterialTheme(darkTheme = false) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            OtherToolsCard(
                title = "Voice and Text Translator",
                icon = {
                    // Placeholder icon
                    Text("T", style = MaterialTheme.typography.titleLarge)
                },
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "OtherToolsCard - Dark Theme")
@Composable
fun OtherToolsCardDarkPreview() {
    KptMaterialTheme(darkTheme = true) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            OtherToolsCard(
                title = "Grammar Check",
                icon = {
                    // Placeholder icon
                    Text("G", style = MaterialTheme.typography.titleLarge)
                },
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

