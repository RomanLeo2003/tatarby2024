package data.repository

import domain.model.Message
import domain.repository.QuizStoryRepository
import domain.service.AIToolsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class FakeQuizStoryRepository(
    private val dispatcher: CoroutineDispatcher,
    private val aiToolsService: AIToolsService
) : QuizStoryRepository {
    private val _chatMessages = MutableStateFlow(listOf<Message>())
    override val chatMessages = _chatMessages

    private val _isTyping = MutableStateFlow(false)
    override val isTyping = _isTyping

    private val _options = MutableStateFlow(emptyList<String>())
    override val options = _options

    private val _optionsTitle = MutableStateFlow("")
    override val optionsTitle = _optionsTitle

    init {
        setInitialOptions()
    }

    private fun setInitialOptions() {
        optionsTitle.update { FakeQuizStory.topicOptionsTitle }
        _options.update { FakeQuizStory.topicOptionsList }
    }

    override suspend fun chooseOption(option: String, words: List<String>) = withContext(dispatcher) {
        if (_chatMessages.value.isEmpty()) {
            addMessage(Message("Темадан башлап хикәя башла: $option"))
            sendRepsonceWithStory(1, 1, words)

        } else {
            val index = options.value.indexOf(option)

            addMessage(Message(option))
            sendRepsonceWithStory(2, index, words)
        }
    }


    private suspend fun sendRepsonceWithStory(level: Int, optionIndex: Int, words: List<String>) {
        withTyping {
            val story = aiToolsService.getStory(level = level, prevText = optionIndex, words = words)

            addMessage(Message(story, false))
        }

        withTyping {
            val options = aiToolsService.getStoryOptions(level = level, prevText = optionIndex, words = words)

            optionsTitle.update { FakeQuizStory.storyOptionsTitle }
            _options.update { options }
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

object FakeQuizStory {
    const val storyText =
        "Борын-борын заманда, Казанның киң урман йөрәгендә, Алсу исемле яшь кыз яшәгән. Үзенең кызыксынучанлыгы белән танылган, ул табигатьне өйрәнергә яраткан. Бер кояшлы көнне, ул моңа кадәр күрмәгән яшерен сукмак ачкан. Кызыксынып, ул аның артыннан китте. Сукмак аны урманның тирәнрәк өлешенә, уртасында борынгы имән агачы торган ачыклыкка илтте. Агачның кәүсәсендә ишек киселгән иде. Алсу аңа таба ниндидер сәер тартылу сизде. Дулкынлану һәм саклык белән ул ишекне ачып эчкә атлады. Үзенең гаҗәпләнүенә каршы, ул үзен җәнлекләр белән тулы, тылсымлы төстәге дөньяда тапты. Җиләк исле җил исеп, һавада кошларның җырлаган тавышлары ишетелә иде. Алсу тирә-юнендә могҗизаларга күз салганда, ачыклыктан өч аерым сукмак китүен күрде."

    const val storyOptionsTitle = "Выберите вариант для продолжения истории:"
    val storyOptions = listOf(
        "Сул сукмак: Алсу алтын төстәге ялтыраучы сукмак буйлап тылсымлы дөнья турында белем эзләп китә.",
        "Урта сукмак: Алсу борынгы агачлар һәм ерактан килгән көлү тавышлары белән тулы маҗаралы сукмактан китә.",
        "Уң сукмак: Алсу уң яктагы дус җәнлекләргә тартылып, яңа дуслар табарга өметләнеп китә."
    )

    val topicOptionsTitle = "Вот несколько тем, с которых можно начать историю:"
    val topicOptionsList =
        listOf("Серле урман сукмагы", "Сихерле көзге", "Югалган патшалык")
}

data class OptionWithWords(
    val option: String,
    val words: List<String>
)
