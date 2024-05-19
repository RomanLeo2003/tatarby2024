package data.service

import domain.service.AIToolsService
import domain.service.AudioResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class FakeAIToolsService(
    private val dispatcher: CoroutineDispatcher,
    private val tatarHttpClient: HttpClient
) : AIToolsService {

    override suspend fun checkGrammar(text: String) = withContext(dispatcher) {
        println("request text $text")
        try {
            tatarHttpClient.post("https://api.llm.translate.tatar/esse_cost/") {
                contentType(ContentType.Application.Json)
                setBody(UserMessage(text))
            }.body<UserMessageAnswer>().answer
        } catch (e: Exception) {
            e.message.orEmpty()
        }
    }

    override suspend fun getTopicToTalkWithBot() = withContext(dispatcher) {
        delay(3000)
        "Һава торышы турында сөйләшик."
    }

    override suspend fun sendMessageToChatBot(text: String) = withContext(dispatcher) {
        try {
            tatarHttpClient.post("https://api.llm.translate.tatar/chat/") {
                contentType(ContentType.Application.Json)
                setBody(UserMessage(text))
            }.body<UserMessageAnswer>().answer
        } catch (e: Exception) {
            e.message.orEmpty()
        }
    }

    override suspend fun getStory(level: Int, prevText: Int, words: List<String>) = withContext(dispatcher) {
        try {
            tatarHttpClient.post("https://api.llm.translate.tatar/story/") {
                contentType(ContentType.Application.Json)
                setBody(QuizStoryRequest(level, prevText, words).also { println("card -> $it") })
            }.body<QuizStoryResponse>().new_text
        } catch (e: Exception) {
            e.message.orEmpty()
        }
    }

    override suspend fun getStoryOptions(
        level: Int,
        prevText: Int,
        words: List<String>
    ) = withContext(dispatcher) {
        try {
            tatarHttpClient.post("https://api.llm.translate.tatar/story_cont/") {
                contentType(ContentType.Application.Json)
                setBody(QuizStoryRequest(level, prevText, words).also { println("card -> $it") })
            }.body<QuizStoryOptionsResponse>().options
        } catch (e: Exception) {
            listOf(e.message.orEmpty())
        }
    }

    override suspend fun textToSpeach(text: String): AudioResponse = withContext(dispatcher) {
        try {
            tatarHttpClient.get("https://tat-tts.api.translate.tatar/listening/") {
                parameter("speaker", "almaz")
                parameter("text", text)
            }.body()
        } catch (e: Exception) {
            AudioResponse("", 0)
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun checkPronunciation(byteArray: ByteArray, text: String) =
        withContext(dispatcher) {
            try {
                val audio = Base64.encode(byteArray)
                tatarHttpClient.post("https://api.llm.translate.tatar/asr/") {
                    contentType(ContentType.Application.Json)
                    setBody(PronunciationRequest(audio, text))
                }.bodyAsText()
            } catch (e: Exception) {
                e.message.orEmpty()
            }
        }
}

@Serializable
data class UserMessage(val user_message: String)

@Serializable
data class UserMessageAnswer(val answer: String)

@Serializable
data class PronunciationRequest(val audio: String, val text: String)

@Serializable
data class QuizStoryRequest(val level: Int, val prev_text: Int, val words: List<String>)

@Serializable
data class QuizStoryResponse(val new_text: String)

@Serializable
data class QuizStoryOptionsResponse(val options: List<String>)
