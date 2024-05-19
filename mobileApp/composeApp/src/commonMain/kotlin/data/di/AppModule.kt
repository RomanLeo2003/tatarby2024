package data.di

import data.network.TatarHttpClient
import data.repository.ChatBotRepositoryImpl
import data.repository.FakeQuizStoryRepository
import data.repository.FakeWordsRepository
import data.service.FakeAIToolsService
import domain.repository.ChatBotRepository
import domain.repository.QuizStoryRepository
import domain.repository.WordsRepository
import domain.service.AIToolsService
import domain.service.AudioPlayerService
import domain.service.AudioRecorderService
import domain.service.LocalTtsService
import domain.service.NavigateToLink
import domain.service.buildAudioPlayerService
import domain.service.buildAudioRecorderService
import domain.service.buildLocalTtsService
import domain.service.buildNavigateToLink
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
import ui.features.textbooks.TextBooksViewModel

val appModule = module {
    single(named(Dispatcher.Io)) { Dispatchers.IO }
    single { TatarHttpClient.provideHttpClient() }

    single<AIToolsService> {
        FakeAIToolsService(
            dispatcher = get(named(Dispatcher.Io)),
            tatarHttpClient = get()
        )
    }

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

    single<AudioPlayerService> {
        buildAudioPlayerService()
    }

    single<AudioRecorderService> {
        buildAudioRecorderService()
    }

    single<LocalTtsService> {
        buildLocalTtsService()
    }

    single<NavigateToLink> {
        buildNavigateToLink()
    }

    singleOf(::FakeWordsRepository) {
        bind<WordsRepository>()
    }


    factoryOf(::ChatBotViewModel)
    factoryOf(::QuizStoryViewModel)
    factoryOf(::MyWordsViewModel)
    factoryOf(::EssayViewModel)
    factoryOf(::PronunciationVM)
    factoryOf(::TextBooksViewModel)
}

enum class Dispatcher {
    Main, Io
}