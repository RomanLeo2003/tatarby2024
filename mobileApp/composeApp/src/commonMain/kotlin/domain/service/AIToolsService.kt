package domain.service

import kotlinx.serialization.Serializable

interface AIToolsService {

    /**
     * Checks grammar and return feedback
     */
    suspend fun checkGrammar(text: String): String


    suspend fun getTopicToTalkWithBot(): String
    suspend fun sendMessageToChatBot(text: String): String


    suspend fun getStory(level: Int, prevText: Int, words: List<String>): String
    suspend fun getStoryOptions(level: Int, prevText: Int, words: List<String>): List<String>

    suspend fun textToSpeach(text: String): AudioResponse
    suspend fun checkPronunciation(byteArray: ByteArray, text: String): String
}

@Serializable
data class AudioResponse(
    val wav_base64: String,
    val sample_rate: Int
)