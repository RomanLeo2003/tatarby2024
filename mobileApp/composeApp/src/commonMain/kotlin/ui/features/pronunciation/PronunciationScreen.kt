package ui.features.pronunciation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import tatarby.composeapp.generated.resources.Res
import tatarby.composeapp.generated.resources.continuee
import tatarby.composeapp.generated.resources.feedback
import tatarby.composeapp.generated.resources.loading
import tatarby.composeapp.generated.resources.microphone
import tatarby.composeapp.generated.resources.pause
import tatarby.composeapp.generated.resources.pronounce_this_text
import tatarby.composeapp.generated.resources.pronunciation_check
import tatarby.composeapp.generated.resources.volume
import ui.common.composable.TopAppBarWithTyping
import ui.noRippleClickable
import ui.theme.Gray500
import ui.theme.Green200
import ui.theme.Green800

@Composable
fun PronunciationRoute(navigateBack: () -> Unit, viewModel: PronunciationVM = koinInject()) {
    val recordingUiState by viewModel.recordingUiState.collectAsState()
    val textToPronounce by viewModel.textToPronounce.collectAsState()

    PronunciationScreen(
        modifier = Modifier.fillMaxSize(),
        navigateBack = navigateBack,
        onStartRecording = viewModel::onStartRecording,
        onStopRecording = viewModel::onStopRecording,
        recordingUiState = recordingUiState,
        textToPronounce = textToPronounce,
        onContinueClick = viewModel::onContinueClick,
        onPronounceClick = viewModel::pronounceText
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PronunciationScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    recordingUiState: RecordingUiState,
    textToPronounce: String,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onContinueClick: () -> Unit,
    onPronounceClick: () -> Unit
) {
    Column(modifier = modifier) {
        TopAppBarWithTyping(
            title = stringResource(Res.string.pronunciation_check),
            isBotTyping = recordingUiState is RecordingUiState.Loading,
            loadingText = stringResource(Res.string.loading),
            navigateBack = navigateBack
        )

        Spacer(Modifier.weight(1f))


        Card(
            backgroundColor = Color.White,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(Modifier.fillMaxWidth().padding(16.dp).animateContentSize()) {
                Text(
                    text = stringResource(Res.string.pronounce_this_text),
                    fontWeight = FontWeight.Bold,
                    color = Green800,
                    fontSize = 18.sp
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = textToPronounce,
                        fontWeight = FontWeight.Bold,
                        color = Gray500,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onPronounceClick) {
                        Icon(
                            painterResource(Res.drawable.volume),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(35, 171, 247)
                        )
                    }
                }

                Spacer(Modifier.height(30.dp))

                when (recordingUiState) {
                    RecordingUiState.Initial -> {
                        RecordCircle(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .noRippleClickable(onStartRecording),
                            image = Res.drawable.microphone
                        )
                    }

                    is RecordingUiState.VoiceRecording -> {
                        RecordCircle(
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .noRippleClickable(onStopRecording),
                            image = Res.drawable.pause,
                        )

                        Text(
                            text = recordingUiState.seconds.formatSeconds(),
                            fontWeight = FontWeight.Bold,
                            color = Gray500,
                            fontSize = 16.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }

                    RecordingUiState.Loading -> {
                        RecordCircle(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            image = Res.drawable.microphone
                        )
                    }

                    is RecordingUiState.Success -> {
                        Text(
                            text = stringResource(Res.string.feedback),
                            fontWeight = FontWeight.Bold,
                            color = Green800,
                            fontSize = 18.sp,
                        )

                        Text(
                            text = recordingUiState.feedback,
                            fontWeight = FontWeight.Bold,
                            color = Gray500,
                            fontSize = 16.sp,
                        )

                        Spacer(Modifier.height(10.dp))

                        Button(
                            onContinueClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Green800),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            elevation = ButtonDefaults.elevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 0.dp
                            )
                        ) {
                            Text(
                                text = stringResource(Res.string.continuee),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RecordCircle(modifier: Modifier = Modifier, image: DrawableResource) {
    Surface(
        shape = CircleShape,
        color = Green200,
        modifier = modifier
    ) {
        Icon(
            painterResource(image),
            contentDescription = null,
            modifier = Modifier.padding(10.dp).size(34.dp),
            tint = Color(35, 171, 247)
        )
    }
}

fun Int.formatSeconds(): String {
    val minutes = this / 60
    val remainingSeconds = this % 60
    val minutesString = if (minutes < 10) "0$minutes" else minutes.toString()
    val secondsString =
        if (remainingSeconds < 10) "0$remainingSeconds" else remainingSeconds.toString()
    return "$minutesString:$secondsString"
}