package ui.common.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import domain.model.Message
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.TatarskiAppBackground
import ui.theme.Green200
import ui.theme.Green300
import ui.theme.Green800
import ui.theme.TatarskiTheme


@Composable
fun MessageCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape,
    text: String,
    cardColor: Color
) {
    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = cardColor
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
            color = Green800
        )
    }
}

fun getMessageShape(isSenderMe: Boolean, corner: Dp = 15.dp) = when {
    isSenderMe -> RoundedCornerShape(
        topStart = corner,
        topEnd = corner,
        bottomEnd = 0.dp,
        bottomStart = corner
    )

    else -> RoundedCornerShape(
        topStart = corner,
        topEnd = corner,
        bottomEnd = corner,
        bottomStart = 0.dp
    )
}

fun getMessageColor(isSenderMe: Boolean) = when {
    isSenderMe -> Green200
    else -> Green300
}


@Preview
@Composable
private fun MessageCardPreview() {
    TatarskiTheme {
        val shape = getMessageShape(true)
        val color = getMessageColor(true)
        MessageCard(shape = shape, text = "Hello aboba", cardColor = color)
    }
}

@Composable
fun MessagesLazyColumn(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    footer: @Composable () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
        reverseLayout = true
    ) {
        item {
            footer()
        }

        items(messages.reversed()) { message ->
            val shape = getMessageShape(message.isSenderMe)
            val color = getMessageColor(message.isSenderMe)

            Box(Modifier.fillMaxWidth()) {
                MessageCard(
                    shape = shape,
                    text = message.text,
                    cardColor = color,
                    modifier = Modifier
                        .align(if (message.isSenderMe) Alignment.CenterEnd else Alignment.CenterStart)
                        .widthIn(max = 300.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MessagesLazyColumnPrev() {
    val messages = listOf(
        Message("Hello aboba"),
        Message("Hello", false),
        Message("How is your day today", false)
    )

    TatarskiTheme {
        TatarskiAppBackground {
            MessagesLazyColumn(messages = messages)
        }
    }
}