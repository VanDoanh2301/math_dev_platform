package com.dev.devmath.core.feature.home.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.component.KptFilledButton
import com.dev.devmath.core.designsystem.icon.AppIcons
import com.dev.devmath.core.designsystem.theme.KptTheme
import com.dev.devmath.core.designsystem.theme.backgroundLight
import com.dev.devmath.core.designsystem.theme.kptLightTheme
import com.dev.devmath.core.feature.home.main.component.HomeworkCard
import com.dev.devmath.core.feature.home.main.component.OtherToolsCard
import com.dev.devmath.core.feature.home.main.component.getChemistryGradientColors
import com.dev.devmath.core.feature.home.main.component.getChemistryTextColor
import com.dev.devmath.core.feature.home.main.component.getMathGradientColors
import com.dev.devmath.core.feature.home.main.component.getMathTextColor
import com.dev.devmath.core.feature.home.main.component.getPhysicGradientColors
import com.dev.devmath.core.feature.home.main.component.getPhysicTextColor
import com.dev.devmath.core.feature.home.main.component.getPremiumGradientColors
import com.dev.devmath.core.feature.home.main.component.getTranslateGradientColors
import com.dev.devmath.core.feature.home.main.component.getTranslateTextColor
import com.dev.devmath.core.ui.resources.Res
import com.dev.devmath.core.ui.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Main Screen for the Home feature
 */
@Composable
fun MainScreen(
    onMathSolveClick: () -> Unit = {},
    onChemistrySolveClick: () -> Unit = {},
    onPhysicSolveClick: () -> Unit = {},
    onTranslateClick: () -> Unit = {},
    onVoiceTranslatorClick: () -> Unit = {},
    onGrammarCheckClick: () -> Unit = {},
    onCameraClick: () -> Unit = {},
    onPremiumUpgradeClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = KptTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(KptTheme.spacing.lg)
    ) {
        item {
            Spacer(modifier = Modifier.height(KptTheme.spacing.md))
            HeaderSection()
        }

        item {
            PremiumBanner(onClick = onPremiumUpgradeClick)
        }

        item {
            Text(
                text = stringResource(Res.string.home_homework_solve),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = KptTheme.spacing.xs)
            )
        }

        item {
            HomeworkCardsGrid(
                onMathSolveClick = onMathSolveClick,
                onChemistrySolveClick = onChemistrySolveClick,
                onPhysicSolveClick = onPhysicSolveClick,
                onTranslateClick = onTranslateClick
            )
        }

        item {
            Text(
                text = stringResource(Res.string.home_other_tools),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = KptTheme.spacing.xs)
            )
        }

        item {
            OtherToolsCards(
                onVoiceTranslatorClick = onVoiceTranslatorClick,
                onGrammarCheckClick = onGrammarCheckClick,
                onCameraClick = onCameraClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(KptTheme.spacing.lg))
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.home_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(KptTheme.spacing.sm)
        ) {

        }
    }
}

@Composable
private fun PremiumBanner(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .paint(painterResource(Res.drawable.img_bg_premium))
            .padding(vertical = KptTheme.spacing.xs)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(KptTheme.spacing.xs),
            modifier = Modifier
                .fillMaxWidth()
                .padding(KptTheme.spacing.md)
                .align(Alignment.CenterStart)
        ) {
            Text(
                stringResource(Res.string.premium_upgrade_title),
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = painterResource(Res.drawable.ic_tick), contentDescription = null)
                Spacer(modifier = Modifier.width(KptTheme.spacing.sm))
                Text(
                    stringResource(Res.string.premium_remove_ads),
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = painterResource(Res.drawable.ic_tick), contentDescription = null)
                Spacer(modifier = Modifier.width(KptTheme.spacing.sm))
                Text(
                    stringResource(Res.string.premium_unlock_features),
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onBackground)
                )
            }

            KptFilledButton(
                onClick = {}, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                )
            ) {
                Text(
                    stringResource(Res.string.premium_upgrade_now),
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun HomeworkCardsGrid(
    onMathSolveClick: () -> Unit,
    onChemistrySolveClick: () -> Unit,
    onPhysicSolveClick: () -> Unit,
    onTranslateClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(KptTheme.spacing.md)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(KptTheme.spacing.md)
        ) {
            HomeworkCard(
                title = stringResource(Res.string.homework_math_solve),
                icon = {
                    Image(
                        painter = painterResource(Res.drawable.ic_home_math),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                gradientColors = getMathGradientColors(),
                onClick = onMathSolveClick,
                modifier = Modifier
                    .weight(1f),
                textColor = getMathTextColor()
            )

            HomeworkCard(
                title = stringResource(Res.string.homework_chemistry_solve),
                icon = {
                    Image(
                        painter = painterResource(Res.drawable.ic_home_chemistry),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                gradientColors = getChemistryGradientColors(),
                onClick = onChemistrySolveClick,
                modifier = Modifier
                    .weight(1f),
                textColor = getChemistryTextColor()
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(KptTheme.spacing.md)
        ) {
            HomeworkCard(
                title = stringResource(Res.string.homework_physic_solve),
                icon = {
                    Image(
                        painter = painterResource(Res.drawable.ic_home_physic),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                gradientColors = getPhysicGradientColors(),
                onClick = onPhysicSolveClick,
                modifier = Modifier
                    .weight(1f),
                textColor = getPhysicTextColor()
            )

            HomeworkCard(
                title = stringResource(Res.string.homework_translate),
                icon = {
                    Image(
                        painter = painterResource(Res.drawable.ic_home_translate),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                gradientColors = getTranslateGradientColors(),
                onClick = onTranslateClick,
                modifier = Modifier
                    .weight(1f),
                textColor = getTranslateTextColor()
            )
        }
    }
}

@Composable
private fun OtherToolsCards(
    onVoiceTranslatorClick: () -> Unit,
    onGrammarCheckClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(KptTheme.spacing.md)
    ) {
        OtherToolsCard(
            title = stringResource(Res.string.tool_voice_translator),
            icon = {
                Image(
                    painter = painterResource(Res.drawable.img_home_translate),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            },
            onClick = onVoiceTranslatorClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer
        )

        OtherToolsCard(
            title = stringResource(Res.string.tool_grammar_check),
            icon = {
                Image(
                    painter = painterResource(Res.drawable.ic_home_grammar),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            },
            onClick = onGrammarCheckClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        OtherToolsCard(
            title = stringResource(Res.string.tool_camera),
            icon = {
                androidx.compose.material3.Icon(
                    imageVector = AppIcons.Camera,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            onClick = onCameraClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(showBackground = false)
@Composable
fun MainScreenLightPreview() {
    KptMaterialTheme(
        theme = kptLightTheme {
            colors {
                background = backgroundLight
            }
        }
    ) {
        MainScreen()
    }
}

@Preview()
@Composable
fun MainScreenDarkPreview() {
    KptMaterialTheme(darkTheme = true) {
        MainScreen()
    }
}
