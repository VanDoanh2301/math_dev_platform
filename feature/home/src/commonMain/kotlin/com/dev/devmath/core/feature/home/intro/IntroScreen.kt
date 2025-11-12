package com.dev.devmath.core.feature.home.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.designsystem.component.KptFilledButton
import com.dev.devmath.core.designsystem.theme.KptTheme
import com.dev.devmath.core.feature.home.intro.component.PagerIndicator
import com.dev.devmath.core.ui.resources.Res
import com.dev.devmath.core.ui.resources.*
import com.dev.devmath.core.ui.resources.img_background_splash
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

const val PAGE_SIZE = 3
const val WEIGHT_SPACING = 0.5f
const val HEIGHT_BUTTON = 46

@Composable
fun IntroScreen() {
    val pagerState = rememberPagerState(0, 0F) { introList.size }
    val scope = rememberCoroutineScope()
    val buttonTextState by remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> Res.string.next
                1 -> Res.string.next
                2 -> Res.string.get_started
                else -> Res.string.get_started
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.img_background_splash),
                contentScale = ContentScale.FillBounds
            ),
        contentAlignment = Alignment.Center
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Spacer(modifier = Modifier.weight(1f))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.padding(horizontal = KptTheme.spacing.xl).fillMaxWidth()
            ) { page ->
                IntroItem(introList[page])
            }

            Spacer(modifier = Modifier.weight(1f))

            PagerIndicator(
                pagerState = pagerState,
                count = 3,
            )

            KptFilledButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                shape = KptTheme.shapes.extraLarge,
                modifier = Modifier
                    .padding(KptTheme.spacing.md)
                    .fillMaxWidth()
                    .height(HEIGHT_BUTTON.dp)
            ) {
                Text(
                    stringResource(buttonTextState),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Composable
fun IntroItem(intro: Intro) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(intro.imageRes),
            modifier = Modifier.fillMaxWidth().aspectRatio(9/16f),
            contentDescription = "content image"
        )

        Spacer(modifier = Modifier.height(KptTheme.spacing.lg))

        Text(
            text = stringResource(intro.title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

    }
}


data class Intro(
    val title: StringResource,
    val content: StringResource,
    val imageRes: DrawableResource
)

private val introList = listOf(
    Intro(
        title = Res.string.intro_title_one,
        content = Res.string.intro_content_one,
        imageRes = Res.drawable.intro_1
    ),
    Intro(
        title = Res.string.intro_title_two,
        content = Res.string.intro_content_two,
        imageRes = Res.drawable.intro_2
    ),
    Intro(
        title = Res.string.intro_title_three,
        content = Res.string.intro_content_three,
        imageRes = Res.drawable.intro_3
    )
)


@Preview(showBackground = true)
@Composable
private fun IntroPreview() {
    KptMaterialTheme {
        IntroScreen()
    }
}