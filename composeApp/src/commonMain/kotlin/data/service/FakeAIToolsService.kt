package data.service

import domain.model.QuizStory
import domain.service.AIToolsService
import data.repository.FakeQuizStory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeAIToolsService(
    private val dispatcher: CoroutineDispatcher
) : AIToolsService {

    override suspend fun checkGrammar(text: String) = withContext(dispatcher) {
        delay(2000)
        "Гомумән әйбәт, әмма грамматика өстендә күбрәк эшләргә кирәк."
    }

    override suspend fun getTopicToTalkWithBot() = withContext(dispatcher) {
        delay(3000)
        "Һава торышы турында сөйләшик."
    }

    override suspend fun sendMessageToChatBot(text: String) = withContext(dispatcher) {
        delay(2000)
        "Син шундый кызыклы, тагын нәрсә дә булса сөйлә."
    }

    override suspend fun chooseOptionQuizStory(option: String) = withContext(dispatcher) {
        delay(2000)
        QuizStory(
            story = FakeQuizStory.storyText,
            options = FakeQuizStory.storyOptions
        )
    }
}