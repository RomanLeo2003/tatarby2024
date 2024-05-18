package domain.service

import domain.model.QuizStory

interface AIToolsService {

    /**
     * Checks grammar and return feedback
     */
    suspend fun checkGrammar(text: String): String


    suspend fun getTopicToTalkWithBot(): String
    suspend fun sendMessageToChatBot(text: String): String


    suspend fun chooseOptionQuizStory(option: String): QuizStory
}