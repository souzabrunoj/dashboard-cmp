package br.com.souzabrunoj.storekmp.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TIMEOUT_DURATION: Long = 60_000

fun networkModule(
    baseUrl: String,
    engine: HttpClientEngine,
    urlProtocol: URLProtocol = URLProtocol.HTTPS
): HttpClient {
    return HttpClient(engine) {
        expectSuccess = true
        defaultRequest {
            url {
                host = baseUrl
                url { protocol = urlProtocol }
            }
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }

        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_DURATION
            connectTimeoutMillis = TIMEOUT_DURATION
            socketTimeoutMillis = TIMEOUT_DURATION
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
}
