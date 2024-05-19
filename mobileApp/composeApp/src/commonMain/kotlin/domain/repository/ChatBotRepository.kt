package domain.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatBotRepository {
    val chatMessages: Flow<List<Message>>
    val isTyping: Flow<Boolean>

    suspend fun sendMessage(message: Message)
}