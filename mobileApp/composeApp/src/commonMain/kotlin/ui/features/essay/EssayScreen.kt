package ui.features.essay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.check_grammar
import tatarby.composeapp.generated.resources.continuee
import tatarby.composeapp.generated.resources.essay
import tatarby.composeapp.generated.resources.loading
import ui.TatarskiAppBackground
import ui.common.composable.TopAppBarWithTyping
import ui.theme.TatarskiTheme

@Composable
fun EssayRoute(navigateBack: () -> Unit, viewModel: EssayViewModel = koinInject()) {
    val uiState by viewModel.uiState.collectAsState()

    EssayScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        onEssayTextChange = viewModel::onEssayUpdate,
        onButtonClick = {
            if (uiState.isEssayTyping) viewModel.checkGrammar()
            else viewModel.onContinueClick()
        }
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun EssayScreen(
    uiState: EssayUiState,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit = {},
    onEssayTextChange: (String) -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    val buttonText = when {
        uiState.isEssayTyping -> stringResource(Res.string.check_grammar)
        else -> stringResource(Res.string.continuee)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarWithTyping(
                isBotTyping = uiState.isLoading,
                title = stringResource(Res.string.essay),
                navigateBack = navigateBack,
                loadingText = stringResource(Res.string.loading)
            )
        },
        backgroundColor = Color.Transparent
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.BottomCenter
        ) {

            EssayCard(
                isTextFieldEnabled = uiState.isEssayTyping,
                topicTitle = uiState.topic,
                essayText = uiState.essayText,
                buttonText = buttonText,
                feedback = uiState.grammarFeedback,
                modifier = Modifier.padding(20.dp),
                onButtonClick = onButtonClick,
                onEssayTextChange = onEssayTextChange
            )
        }
    }
}

@Preview
@Composable
private fun EssayScreenPreview() {
    val uiState = EssayUiState(
        grammarFeedback = "Good enough",
        topic = "Should Students get limited access to the Internet?"
    )

    TatarskiTheme {
        TatarskiAppBackground {
            EssayScreen(uiState)
        }
    }
}
