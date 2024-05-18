package ui.features.quizstory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Message
import domain.repository.QuizStoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class QuizStoryUiState(
    val chatMessages: List<Message> = emptyList(),
    val isBotTyping: Boolean = false,
    val options: List<String> = emptyList(),
    val optionsTitle: String = ""
)

class QuizStoryViewModel(
    private val quizStoryRepository: QuizStoryRepository
): ViewModel() {

    val uiState = combine(
        quizStoryRepository.chatMessages,
        quizStoryRepository.isTyping,
        quizStoryRepository.options,
        quizStoryRepository.optionsTitle,
        ::QuizStoryUiState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = QuizStoryUiState()
    )

    fun chooseOption(option: String) {
        viewModelScope.launch {
            quizStoryRepository.chooseOption(option)
        }
    }
}