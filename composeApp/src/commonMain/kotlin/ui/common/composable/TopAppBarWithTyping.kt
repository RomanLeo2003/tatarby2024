package ui.common.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.bot_typing
import tatarby.composeapp.generated.resources.chat_bot
import tatarby.composeapp.generated.resources.left_arrow

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TopAppBarWithTyping(
    title: String,
    modifier: Modifier = Modifier,
    isBotTyping: Boolean = false,
    loadingText: String = stringResource(Res.string.bot_typing),
    navigateBack: () -> Unit = {},
) {
    TopAppBar(modifier = modifier, backgroundColor = Color.Transparent, elevation = 0.dp) {
        IconButton(onClick = navigateBack) {
            Icon(
                painter = painterResource(Res.drawable.left_arrow),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 20.sp
        )
        Spacer(Modifier.weight(1f))

        if (isBotTyping)
            TypingBar(Modifier.padding(end = 20.dp), loadingText)
    }

}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun TopAppBarWithTypingPrev() {
    TopAppBarWithTyping(isBotTyping = true, title = stringResource(Res.string.chat_bot))
}