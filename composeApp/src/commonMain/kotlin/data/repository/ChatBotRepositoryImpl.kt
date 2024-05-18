package data.repository

import domain.model.Message
import domain.repository.ChatBotRepository
import domain.service.AIToolsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class ChatBotRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val aiToolsService: AIToolsService
) : ChatBotRepository {
    private val _chatMessages = MutableStateFlow(listOf<Message>())
    override val chatMessages = _chatMessages

    private val _isTyping = MutableStateFlow(false)
    override val isTyping = _isTyping

    override suspend fun sendMessage(message: Message) {
        if (message.text.isEmpty()) return

        addMessage(message.copy(isSenderMe = true))
        askForRespond(message.text)
    }

    private suspend fun askForRespond(messageText: String) {
        if (_chatMessages.value.size == 1)
            sendFirstMessagesWithTopic()
        else
            sendMessageToBotServer(messageText)
    }

    private suspend fun sendMessageToBotServer(messageText: String) {
        withTyping {
            val response = aiToolsService.sendMessageToChatBot(messageText)
            addMessage(Message(response, false))
        }
    }

    private suspend fun sendFirstMessagesWithTopic() = withContext(dispatcher) {
        withTyping {
            delay(2000)
            addMessage(Message("Сәлам", false))
        }

        withTyping {
            val response = aiToolsService.getTopicToTalkWithBot()
            addMessage(Message(response, false))
        }
    }

    private fun addMessage(message: Message) {
        _chatMessages.update { it + message }
    }

    private suspend fun withTyping(block: suspend () -> Unit) = try {
        _isTyping.update { true }
        block()
    } catch (e: Exception) {
        //TODO handle exception
    } finally {
        _isTyping.update { false }
    }
}