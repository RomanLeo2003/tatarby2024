package data.di

import data.repository.ChatBotRepositoryImpl
import data.repository.FakeQuizStoryRepository
import data.repository.FakeWordsRepository
import data.service.FakeAIToolsService
import domain.repository.ChatBotRepository
import domain.repository.QuizStoryRepository
import domain.repository.WordsRepository
import domain.service.AIToolsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ui.features.chatbot.ChatBotViewModel
import ui.features.essay.EssayViewModel
import ui.features.mywords.MyWordsViewModel
import ui.features.pronunciation.PronunciationVM
import ui.features.quizstory.QuizStoryViewModel

val appModule = module {
    single(named(Dispatcher.Io)) { Dispatchers.IO }
    single<AIToolsService> { FakeAIToolsService(dispatcher = get(named(Dispatcher.Io))) }

    single<ChatBotRepository> {
        ChatBotRepositoryImpl(
            dispatcher = get(named(Dispatcher.Io)),
            aiToolsService = get()
        )
    }
    single<QuizStoryRepository> {
        FakeQuizStoryRepository(
            dispatcher = get(named(Dispatcher.Io)),
            aiToolsService = get()
        )
    }

    singleOf(::FakeWordsRepository) {
        bind<WordsRepository>()
    }

    factoryOf(::ChatBotViewModel)
    factoryOf(::QuizStoryViewModel)
    factoryOf(::MyWordsViewModel)
    factoryOf(::EssayViewModel)
    factoryOf(::PronunciationVM)
}

enum class Dispatcher {
    Main, Io
}