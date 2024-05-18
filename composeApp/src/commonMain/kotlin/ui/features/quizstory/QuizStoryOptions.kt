package ui.features.quizstory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.repository.FakeQuizStory
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.TatarskiAppBackground
import ui.theme.Green800
import ui.theme.TatarskiTheme

@Composable
fun QuizStoryOptions(
    modifier: Modifier = Modifier,
    title: String = "",
    options: List<String> = emptyList(),
    onOptionClick: (String) -> Unit = {}
) {
    Surface(
        modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(3.dp, Green800)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Green800,
                modifier = Modifier.padding(vertical = 10.dp),
                fontSize = 18.sp
            )

            options.forEachIndexed { index, op ->
                val option = "${index + 1}) $op"

                QuizStoryItem(
                    text = option,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    onClick = { onOptionClick(option) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuizStoryOptionsPreview() {
    val title = FakeQuizStory.storyOptionsTitle
    val options = FakeQuizStory.storyOptions


    TatarskiTheme {
        TatarskiAppBackground {
            QuizStoryOptions(
                title = title,
                options = options,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuizStoryItem(modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier,
        border = BorderStroke(2.dp, Green800),
        backgroundColor =  Color.Transparent,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = Green800,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun QuizStoryItemPreview() {
    val options = FakeQuizStory.storyOptions

    QuizStoryItem(text = "1) ${options.first()}", modifier = Modifier.fillMaxWidth())
}

