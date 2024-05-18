package ui.common.composable

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.theme.Green200


@Composable
fun DotsTyping(
    dotColor: Color = Green200,
    dotSize: Dp = 6.dp,
    delay: Int = 300,
    spaceBetween: Dp = 3.dp
) {
    val maxOffset = 10f
    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .offset(y = -offset.dp)
            .background(
                color = dotColor,
                shape = CircleShape
            )
    )
    val infiniteTransition = rememberInfiniteTransition(label = "")
    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delay * 4
                0f at delay using LinearEasing
                maxOffset at delay + delay using LinearEasing
                0f at delay + delay * 2
            }
        ), label = ""
    )
    val offset1 by animateOffsetWithDelay(delay * 2)
    val offset2 by animateOffsetWithDelay(delay)
    val offset3 by animateOffsetWithDelay(delay * 2)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        Dot(offset1)
        Spacer(Modifier.width(spaceBetween))
        Dot(offset2)
        Spacer(Modifier.width(spaceBetween))
        Dot(offset3)
    }
}