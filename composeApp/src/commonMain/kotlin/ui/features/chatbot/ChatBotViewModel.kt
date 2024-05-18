package ui.features.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.Message
import domain.repository.ChatBotRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatBotUiState(
    val chatMessages: List<Message> = emptyList(),
    val isBotTyping: Boolean = false,
    val inputText: String = ""
)

class ChatBotViewModel(
    private val chatBotRepository: ChatBotRepository
): ViewModel() {

    private val inputText = MutableStateFlow("")

    val uiState = combine(
        chatBotRepository.chatMessages,
        chatBotRepository.isTyping,
        inputText,
        ::ChatBotUiState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = ChatBotUiState()
    )


    fun onSendMessageClick() {
        val text = inputText.value
        inputText.update { "" }
        sendMessage(text)
    }

    fun onInputUpdate(newValue: String) = inputText.update { newValue }


    private fun sendMessage(text: String) {
        viewModelScope.launch {
            chatBotRepository.sendMessage(Message(text = text))
        }
    }
}