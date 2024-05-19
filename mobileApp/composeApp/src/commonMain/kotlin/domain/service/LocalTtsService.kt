package domain.service

expect fun buildLocalTtsService(): LocalTtsService

fun interface LocalTtsService {

    fun pronounce(text: String)
}