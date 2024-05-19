package data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object TatarHttpClient {

    fun provideHttpClient() = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            logger = KtorLogger{
                println("ktorlogger -> $it")
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 70000 // 70 секунд
            connectTimeoutMillis = 70000
            socketTimeoutMillis = 70000
        }
//        defaultRequest {
//            url {
//                protocol = URLProtocol.HTTPS
//                host = "api.llm.translate.tatar"
//            }
//        }
    }
}

fun interface KtorLogger: Logger
