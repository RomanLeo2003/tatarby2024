package ui.features.chatbot

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.TatarskiAppBackground
import ui.common.composable.InputMessageField
import ui.common.composable.MessagesLazyColumn
import ui.common.composable.TopAppBarWithTyping
import ui.theme.TatarskiTheme
import domain.model.Message
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.chat_bot

@Composable
fun ChatBotRoute(navigateBack: () -> Unit, viewModel: ChatBotViewModel = koinInject()) {
    val uiState by viewModel.uiState.collectAsState()

    ChatBotScreen(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        uiState = uiState,
        onInputMessageChange = viewModel::onInputUpdate,
        onSendMessageClick = viewModel::onSendMessageClick,
        navigateBack = navigateBack
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ChatBotScreen(
    uiState: ChatBotUiState,
    modifier: Modifier = Modifier,
    onInputMessageChange: (String) -> Unit = {},
    onSendMessageClick: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {

    Scaffold(
        modifier = modifier, topBar = {
            TopAppBarWithTyping(
                isBotTyping = uiState.isBotTyping,
                title = stringResource(Res.string.chat_bot),
                navigateBack = navigateBack
            )
        },
        bottomBar = {
            InputMessageField(
                modifier = Modifier.padding(20.dp),
                value = uiState.inputText,
                onValueChange = onInputMessageChange,
                onSendMessageClick = onSendMessageClick
            )
        },
        backgroundColor = Color.Transparent
    ) {
        MessagesLazyColumn(
            messages = uiState.chatMessages,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}


@Preview
@Composable
private fun ChatBotScreenPreview() {
    val messages = listOf(
        Message("Hello aboba"),
        Message("Hello", false),
        Message("How is your day today", false)
    )

    val uiState = ChatBotUiState(chatMessages = messages)

    TatarskiTheme {
        TatarskiAppBackground {
            ChatBotScreen(uiState)
        }
    }
}
