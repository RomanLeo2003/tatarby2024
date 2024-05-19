package domain.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow

interface QuizStoryRepository {
    val chatMessages: Flow<List<Message>>
    val isTyping: Flow<Boolean>
    val options: Flow<List<String>>
    val optionsTitle: Flow<String>

    suspend fun chooseOption(option: String, words: List<String>)
}