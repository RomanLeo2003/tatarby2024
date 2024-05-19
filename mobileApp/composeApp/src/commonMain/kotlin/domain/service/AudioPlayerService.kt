package domain.service

interface AudioPlayerService {

    fun playAudio(byteArray: ByteArray, sampleRate: Int)
    fun stopAudio()
}

expect fun buildAudioPlayerService(): AudioPlayerService