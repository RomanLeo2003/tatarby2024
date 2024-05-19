package ui.features.essay

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.check_grammar
import tatarby.composeapp.generated.resources.feedback
import tatarby.composeapp.generated.resources.input_text
import tatarby.composeapp.generated.resources.write_essay_on_topic
import ui.theme.Gray800
import ui.theme.Green800

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EssayCard(
    modifier: Modifier = Modifier,
    topicTitle: String,
    essayText: String,
    buttonText: String,
    feedback: String,
    isTextFieldEnabled: Boolean = true,
    onButtonClick: () -> Unit = {},
    onEssayTextChange: (String) -> Unit = {}
) {
    Card(modifier = modifier, backgroundColor = Color.White, shape = RoundedCornerShape(20.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(Res.string.write_essay_on_topic),
                fontWeight = FontWeight.Bold,
                color = Green800,
                modifier = Modifier.padding(vertical = 10.dp),
                fontSize = 20.sp
            )

            Text(
                text = topicTitle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp),
                fontSize = 18.sp,
                color = Gray800
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                enabled = isTextFieldEnabled,
                value = essayText,
                onValueChange = onEssayTextChange,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(236, 236, 236, 255),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(Res.string.input_text),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))

            if (essayText.isNotEmpty())
                Text(
                    text = "${essayText.length}/200",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 18.sp
                )

            Spacer(modifier = Modifier.height(20.dp))

            if (feedback.isNotEmpty())
                Column {
                    Text(
                        text = stringResource(Res.string.feedback),
                        fontWeight = FontWeight.Bold,
                        color = Green800,
                        fontSize = 18.sp
                    )

                    Text(
                        text = feedback,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontSize = 18.sp
                    )
                }


            Button(
                onClick = onButtonClick,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Green800
                ),
                shape = RoundedCornerShape(25.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    text = buttonText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
private fun EssayCardPreview() {
    EssayCard(
        topicTitle = "Should Students get limited access to the Internet?",
        essayText = "",
        buttonText = stringResource(Res.string.check_grammar),
        feedback = "Good enough"
    )
}