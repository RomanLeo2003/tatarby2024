package ui.features.pronunciation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.service.AIToolsService
import domain.service.AudioPlayerService
import domain.service.AudioRecorderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class PronunciationVM(
    private val aiToolsService: AIToolsService,
    private val audioPlayerService: AudioPlayerService,
    private val audioRecorderService: AudioRecorderService
) : ViewModel() {

    private val _recordingUiState = MutableStateFlow<RecordingUiState>(RecordingUiState.Initial)
    val recordingUiState: StateFlow<RecordingUiState> = _recordingUiState

    private val _textToPronounce = MutableStateFlow("")
    val textToPronounce: StateFlow<String> = _textToPronounce

    private var job: Job? = null

    init {
        updateTextToPronounceWithRandom()
    }

    private fun updateTextToPronounceWithRandom() {
        val sentences = listOf(
            "Мәче һәм тычкан бер йортта бергә яшәгәннәр.",
            "Әни кибеткә барып, бер атнага азык-төлек сатып алды.",
            "Мин китап укырга һәм китапханәгә барырга яратам.",
            "Кичә без паркта йөрдек һәм үрдәкләрне икмәк белән тукландырдык.",
            "Әти тәмле ризыклар әзерли һәм пироглар пешерә белә.",
            "Минем елның иң яраткан вакыты-көз, Яфраклар саргая һәм төшә.",
            "Мәктәптә без математика, Тарих һәм әдәбият кебек бик күп кызыклы фәннәрне өйрәнәбез.",
            "Абыем спорт белән мавыга һәм баскетбол секциясенә йөри.",
            "Кичә без дусларым белән футбол уйнадык һәм күп гол керттек.",
            "Бабай яулык һәм чулпы бәйләргә ярата һәм аларны һәрвакыт оныкларына бүләк итә.",
            "Минем этем йөгерергә һәм туп уйнарга ярата, ул бик акыллы һәм күңелле.",
            "Кичә мин кинотеатрга бардым һәм кызыклы фильм карадым.",
            "Минем яраткан ризыгым - пицца, мин аны көн саен ашый алам.",
            "Җәй - ял һәм сәяхәт вакыты, мин башка шәһәрләргә һәм илләргә барырга яратам.",
            "Минем хыялым - космонавт булу һәм космоска очу, мин үз хыялымны тормышка ашырырга өйрәнәм һәм тырышам."
        )

        _textToPronounce.update { sentences.random() }
    }

    fun onStartRecording() {
        job = viewModelScope.launch(Dispatchers.IO) {
            audioRecorderService.startRecording()
            var seconds = 0
            while (true) {
                _recordingUiState.update { RecordingUiState.VoiceRecording(seconds) }
                delay(1000)
                seconds++
            }
        }
    }

    fun onStopRecording() {
        job?.cancel()

        viewModelScope.launch(Dispatchers.IO) {
            _recordingUiState.update { RecordingUiState.Loading }
            audioRecorderService.stopRecording()
            val recordedFile = audioRecorderService.getRecordedFile() ?: byteArrayOf()
            val response = aiToolsService.checkPronunciation(recordedFile, _textToPronounce.value)
            println("pronunciation quest -> ${recordedFile.toList()}")
            _recordingUiState.update { RecordingUiState.Success(response) }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun pronounceText() {
        viewModelScope.launch {
            val response = aiToolsService.textToSpeach(textToPronounce.value)
            val byteArray = Base64.decode(response.wav_base64)
            audioPlayerService.playAudio(byteArray, response.sample_rate)
        }
    }

    fun onContinueClick() {
        _recordingUiState.update { RecordingUiState.Initial }
        updateTextToPronounceWithRandom()
    }
}

sealed interface RecordingUiState {
    data object Loading: RecordingUiState
    data class VoiceRecording(val seconds: Int): RecordingUiState
    data class Success(val feedback: String): RecordingUiState
    data object Initial: RecordingUiState
}

