package domain.service

actual fun buildAudioPlayerService(): AudioPlayerService {

    return object : AudioPlayerService{
        override fun playAudio(byteArray: ByteArray, sampleRate: Int) {

        }

        override fun stopAudio() {

        }
    }
}