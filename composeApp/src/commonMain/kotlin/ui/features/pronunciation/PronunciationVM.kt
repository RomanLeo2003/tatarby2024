package ui.features.pronunciation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PronunciationVM : ViewModel() {

    val _recordingUiState = MutableStateFlow<RecordingUiState>(RecordingUiState.Initial)
    val recordingUiState: StateFlow<RecordingUiState> = _recordingUiState

    val _textToPronounce = MutableStateFlow("Мин татар телен өйрәнәм.")
    val textToPronounce: StateFlow<String> = _textToPronounce

    private var job: Job? = null

    fun onStartRecording() {
        job = viewModelScope.launch(Dispatchers.IO) {
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
            delay(4000)
            _recordingUiState.update { RecordingUiState.Success("NotBad") }
        }
    }

    fun onContinueClick() {
        _recordingUiState.update { RecordingUiState.Initial }
        _textToPronounce.update { "Мин мәктәпкә барам."  }
    }
}

sealed interface RecordingUiState {
    data object Loading: RecordingUiState
    data class VoiceRecording(val seconds: Int): RecordingUiState
    data class Success(val feedback: String): RecordingUiState
    data object Initial: RecordingUiState
}

