package domain.service

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

actual fun buildAudioPlayerService(): AudioPlayerService = AndroidAudioPlayer()

class AndroidAudioPlayer: AudioPlayerService {
    private var audioTrack: AudioTrack? = null

    override fun playAudio(byteArray: ByteArray, sampleRate: Int) {
        if (audioTrack == null) {
            val minBufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize,
                AudioTrack.MODE_STREAM
            )
        }

        audioTrack?.apply {
            play()
            write(byteArray, 0, byteArray.size)
        }
    }

    override fun stopAudio() {
        audioTrack?.apply {
            stop()
            release()
        }
        audioTrack = null
    }
}