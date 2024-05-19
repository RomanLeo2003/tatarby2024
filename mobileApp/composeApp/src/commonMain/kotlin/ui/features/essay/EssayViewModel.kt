package ui.features.essay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.service.AIToolsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EssayUiState(
    val isLoading: Boolean = false,
    val essayText: String = "",
    val grammarFeedback: String = "",
    val topic: String = ""
) {
    val isEssayTyping: Boolean = grammarFeedback.isEmpty()
}

class EssayViewModel(
    private val AIToolsService: AIToolsService
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val essayText = MutableStateFlow("")
    private val grammarFeedback = MutableStateFlow("")
    private val topic = MutableStateFlow("")

    init {
        setRandomTopic()
    }

    val uiState = combine(
        isLoading,
        essayText,
        grammarFeedback,
        topic,
        ::EssayUiState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = EssayUiState()
    )

    fun checkGrammar() {
        if (essayText.value.isEmpty()) return

        viewModelScope.launch {
            isLoading.update { true }

            val feedback = AIToolsService.checkGrammar(essayText.value)
            grammarFeedback.update { feedback }

            isLoading.update { false }
        }
    }

    private fun setRandomTopic() {
        val topics = listOf(
            "Укучыларга интернетка чикле керү мөмкинлеге булырга тиешме?",
            "Тәмәке сату тыелырга тиеш.",
            "Шәһәрләшү аркасында пычрану"
        )

        topic.update { topics.random() }
    }

    fun onEssayUpdate(text: String) = essayText.update { text }

    fun onContinueClick() {
        essayText.update { "" }
        grammarFeedback.update { "" }
        setRandomTopic()
    }
}