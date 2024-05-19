package ui.features.quizstory

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import data.repository.FakeQuizStory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.quiz_story
import ui.TatarskiAppBackground
import ui.common.composable.MessagesLazyColumn
import ui.common.composable.TopAppBarWithTyping
import ui.theme.TatarskiTheme

@Composable
fun QuizStoryRoute(navigateBack: () -> Unit, viewModel: QuizStoryViewModel = koinInject()) {
    val uiState by viewModel.uiState.collectAsState()

    QuizStoryScreen(
        modifier = Modifier.fillMaxSize(),
        navigateBack = navigateBack,
        onOptionClick = viewModel::chooseOption,
        uiState = uiState
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun QuizStoryScreen(
    modifier: Modifier = Modifier,
    uiState: QuizStoryUiState,
    onOptionClick: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier, topBar = {
            TopAppBarWithTyping(
                isBotTyping = uiState.isBotTyping,
                title = stringResource(Res.string.quiz_story),
                navigateBack = navigateBack
            )
        },
        backgroundColor = Color.Transparent
    ) {
        MessagesLazyColumn(
            messages = uiState.chatMessages,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            footer = {
                Box(Modifier.fillMaxWidth().animateContentSize(), contentAlignment = Alignment.BottomCenter) {
                    if (!uiState.isBotTyping)
                        QuizStoryOptions(
                            title = uiState.optionsTitle,
                            options = uiState.options,
                            onOptionClick = onOptionClick
                        )
                }
            }
        )
    }
}


@Preview
@Composable
private fun QuizStoryScreenPreview() {
    val title = FakeQuizStory.storyOptionsTitle
    val options = FakeQuizStory.storyOptions

    val uiState = QuizStoryUiState(options = options, optionsTitle = title)

    TatarskiTheme {
        TatarskiAppBackground {
            QuizStoryScreen(uiState = uiState)
        }
    }
}