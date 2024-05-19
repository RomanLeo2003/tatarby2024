package domain.service

actual fun buildAudioRecorderService(): AudioRecorderService {
    return object : AudioRecorderService {
        override fun startRecording() {

        }

        override fun stopRecording() {
        }

        override fun getRecordedFile(): ByteArray? {
            return byteArrayOf()
        }
    }
}