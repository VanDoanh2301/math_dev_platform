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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

const val PAGE_SIZE = 3
const val WEIGHT_SPACING = 0.5f
const val HEIGHT_BUTTON = 42

@Composable
fun IntroScreen() {
    val pagerState = rememberPagerState(0, 0F) { introList.size }
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

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize().align(alignment = Alignment.Center)
        ) { page ->
            IntroItem(introList[page])
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().align(alignment = Alignment.BottomCenter)
        )
        {


            PagerIndicator(
                pagerState = pagerState,
                count = 3,
            )

            KptFilledButton(
                onClick = {},
                shape = KptTheme.shapes.extraLarge,
                modifier = Modifier
                    .padding(KptTheme.spacing.md)
                    .fillMaxWidth()
                    .height(HEIGHT_BUTTON.dp)
            ) {
                Text("stringResource(buttonTextState)", style = MaterialTheme.typography.bodyLarge)
            }


        }
    }

}

@Composable
fun IntroItem(intro: Intro) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = KptTheme.spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(intro.imageRes),
            modifier = Modifier.fillMaxWidth(),
            contentDescription = "content image"
        )

        Spacer(modifier = Modifier.height(KptTheme.spacing.md))

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
        imageRes = Res.drawable.ic_intro_live_location
    ),
    Intro(
        title = Res.string.intro_title_two,
        content = Res.string.intro_content_two,
        imageRes = Res.drawable.ic_intro_receive_alrets
    ),
    Intro(
        title = Res.string.intro_title_three,
        content = Res.string.intro_content_three,
        imageRes = Res.drawable.ic_intro_location_history
    )
)


@Preview
@Composable
private fun IntroPreview() {
    KptMaterialTheme {
        IntroScreen()
    }
}