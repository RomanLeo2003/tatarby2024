package domain.service

import android.app.Application
import android.content.Context
import android.media.MediaRecorder
import androidx.startup.Initializer
import java.io.File
import java.io.FileInputStream

actual fun buildAudioRecorderService(): AudioRecorderService = AudioRecorderImpl()

class AudioRecorderImpl : AudioRecorderService {

    private var recorder: MediaRecorder? = null
    private var output: String = AppContext.get().cacheDir.absolutePath + "/recording.3gp"

    override fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(output)
            prepare()
            start()
        }
    }

    override fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    override fun getRecordedFile(): ByteArray? {
        val file = File(output)
        return if (file.exists()) {
            val inputStream = FileInputStream(file)
            val bytes = inputStream.readBytes()
            inputStream.close()
            bytes
        } else {
            null
        }
    }
}

internal object AppContext {
    private lateinit var application: Application

    fun setUp(context: Context) {
        application = context as Application
    }

    fun get(): Context {
        if(::application.isInitialized.not()) throw Exception("Application context isn't initialized")
        return application.applicationContext
    }
}

internal class AppContextInitializer : Initializer<Context> {
    override fun create(context: Context): Context {
        AppContext.setUp(context.applicationContext)
        return AppContext.get()
    }
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}