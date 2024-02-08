package br.com.souzabrunoj.storekmp.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException

suspend fun <T> safeRequest(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    action: suspend () -> T
): T = withContext(dispatcher) {
    runCatching {
        action()
    }.getOrElse { error ->
        throw when (error) {
            is NetworkException.NoInternetConnection -> error
            is ServerResponseException -> NetworkException.HttpException(
                error.response.status.value,
                error.response.body(),
                error.cause
            )

            is TimeoutCancellationException -> NetworkException.Timeout(error.cause)
            is SerializationException -> NetworkException.Serializable(error.cause?.message)
            else -> NetworkException.UnknownException(error.cause)
        }
    }
}

suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, E> = try {
    val response = request { block() }
    ApiResponse.Success(response.body())
} catch (e: ClientRequestException) {
    ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
} catch (e: ServerResponseException) {
    ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
} catch (e: IOException) {
    ApiResponse.Error.NetworkError
} catch (e: SerializationException) {
    ApiResponse.Error.SerializationError
}

suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {
        response.body()
    } catch (e: SerializationException) {
        null
    }
