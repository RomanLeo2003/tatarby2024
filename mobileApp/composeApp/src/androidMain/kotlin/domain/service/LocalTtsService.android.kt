package domain.service

import android.speech.tts.TextToSpeech
import java.util.Locale

actual fun buildLocalTtsService(): LocalTtsService = LocalTtsServiceImpl

object LocalTtsServiceImpl: LocalTtsService {
    private val tts = TextToSpeech(AppContext.get(), {}).apply {
        language = Locale("tt")
    }

    override fun pronounce(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}