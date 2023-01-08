package com.caesar84mx.swensonforecast.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@ExperimentalAnimationApi
@Composable
fun <S> SlideAnimatedView(
    targetState: S,
    modifier: Modifier = Modifier,
    slideInDurationMillis: Int = 200,
    slideInDelayMillis: Int = 10,
    slideOutDurationMillis: Int = 200,
    slideOutDelayMillis: Int = 10,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            slideIn(
                initialOffset = { IntOffset(it.width / 2, -it.height) },
                animationSpec = tween(
                    durationMillis = slideInDurationMillis,
                    delayMillis = slideInDelayMillis,
                    easing = LinearOutSlowInEasing
                )
            ) with slideOut(
                targetOffset = { IntOffset(it.width / 2, -it.height) },
                animationSpec = tween(
                    durationMillis = slideOutDurationMillis,
                    delayMillis = slideOutDelayMillis,
                    easing = FastOutLinearInEasing
                )
            )
        }
    ) {
        content(it)
    }
}