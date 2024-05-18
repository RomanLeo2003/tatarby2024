package ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.chat
import tatarby.composeapp.generated.resources.chat_bot
import tatarby.composeapp.generated.resources.essay
import tatarby.composeapp.generated.resources.knight
import tatarby.composeapp.generated.resources.pronunciation_check
import tatarby.composeapp.generated.resources.quiz_story
import tatarby.composeapp.generated.resources.search
import tatarby.composeapp.generated.resources.speak
import tatarby.composeapp.generated.resources.trainings
import ui.theme.Green200
import ui.theme.Green800
import ui.theme.TatarskiTheme

@Composable
internal fun HomeRoute(
    navigateToChatBotScreen: () -> Unit,
    navigateToQuizStoryScreen: () -> Unit,
    navigateToEssayScreen: () -> Unit,
    navigateToPronunciation: () -> Unit,
) {
    val onTrainingCardClick = { cardType: TrainingType ->
        when (cardType) {
            TrainingType.ChatBot -> navigateToChatBotScreen()
            TrainingType.QuizStory -> navigateToQuizStoryScreen()
            TrainingType.Pronunciation -> navigateToPronunciation()
            TrainingType.Essay -> navigateToEssayScreen()
        }
    }

    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        trainingTypes = TrainingType.entries,
        onTrainingCardClick = onTrainingCardClick
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable

private fun HomeScreen(
    modifier: Modifier = Modifier,
    onTrainingCardClick: (TrainingType) -> Unit = {},
    trainingTypes: List<TrainingType> = emptyList()
) {


    Column(Modifier.padding(16.dp)) {
        Text(
            text = stringResource(Res.string.trainings) + ":",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(16.dp))


        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier,
            columns = GridCells.Fixed(2)
        ) {
            items(trainingTypes) { trainingType ->
                TrainingCard(
                    modifier = Modifier.aspectRatio(1f),
                    trainingType = trainingType,
                    onClick = {
                        onTrainingCardClick(trainingType)
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
private fun TrainingCard(
    modifier: Modifier = Modifier,
    trainingType: TrainingType = TrainingType.Essay,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        backgroundColor = Green800,
        shape = RoundedCornerShape(20.dp),
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 8.dp)
                .background(Green200, RoundedCornerShape(15.dp))
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(trainingType.iconRes),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(trainingType.titleRes),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
enum class TrainingType @OptIn(ExperimentalResourceApi::class) constructor(
    val iconRes: DrawableResource,
    val titleRes: StringResource
) {
    @OptIn(ExperimentalResourceApi::class)
    ChatBot(iconRes = Res.drawable.chat, titleRes = Res.string.chat_bot),
    @OptIn(ExperimentalResourceApi::class)
    QuizStory(iconRes = Res.drawable.knight, titleRes = Res.string.quiz_story),
    @OptIn(ExperimentalResourceApi::class)
    Pronunciation(iconRes = Res.drawable.speak, titleRes = Res.string.pronunciation_check),
    @OptIn(ExperimentalResourceApi::class)
    Essay(iconRes = Res.drawable.search, titleRes = Res.string.essay)
}

@Preview
@Composable
private fun TrainingCardPrev() {
    TatarskiTheme {
        TrainingCard(
            modifier = Modifier.size(height = 200.dp, width = 200.dp)
        )
    }
}

@Preview()
@Composable
private fun HomeScreenPrev() {
    TatarskiTheme {
        HomeScreen(trainingTypes = TrainingType.entries)
    }
}


