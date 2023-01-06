package com.caesar84mx.swensonforecast.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

@ExperimentalAnimationApi
@Composable
fun <S> SlideAnimatedView(
    targetState: S,
    modifier: Modifier = Modifier,
    scaleInDurationMillis: Int = 200,
    scaleInDelayMillis: Int = 10,
    scaleOutDurationMillis: Int = 200,
    scaleOutDelayMillis: Int = 10,
    content: @Composable AnimatedVisibilityScope.(targetState: S) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            slideIn(
                animationSpec = tween(
                    durationMillis = scaleInDurationMillis,
                    delayMillis = scaleInDelayMillis,
                ),
                initialOffset = { fullSize -> IntOffset(fullSize.width, 0) }
            ) with slideOut(
                animationSpec = tween(
                    durationMillis = scaleOutDurationMillis,
                    delayMillis = scaleOutDelayMillis
                ),
                targetOffset = { fullSize -> IntOffset(fullSize.width, 0) }
            )
        }
    ) {
        content(it)
    }
}