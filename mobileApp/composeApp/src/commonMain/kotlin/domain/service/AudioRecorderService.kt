package domain.service

interface AudioRecorderService {
    fun startRecording()
    fun stopRecording()
    fun getRecordedFile(): ByteArray?
}

expect fun buildAudioRecorderService(): AudioRecorderService
