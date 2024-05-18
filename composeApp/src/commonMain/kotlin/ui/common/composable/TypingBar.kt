package ui.common.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.bot_typing
import ui.theme.Green200
import ui.theme.TatarskiTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TypingBar(
    modifier: Modifier = Modifier,
    text: String = stringResource(Res.string.bot_typing)
) {
    Row(modifier) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = Green200,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.width(4.dp))

        DotsTyping()
    }
}

@Preview
@Composable
private fun TypingBarPrev() {
    TatarskiTheme {
        TypingBar()
    }
}