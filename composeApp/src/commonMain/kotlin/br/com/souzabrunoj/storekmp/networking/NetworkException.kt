package br.com.souzabrunoj.storekmp.networking

sealed class NetworkException(code: Int, message: String) : RuntimeException() {

    data class NoInternetConnection(override val cause: Throwable? = null) : NetworkException(
        code = 1000,
        message = "No internet connection error -> $cause"
    )

    data class Timeout(override val cause: Throwable? = null) : NetworkException(
        code = 1001,
        message = "Timeout error -> $cause"
    )

    data class Serializable(override val message: String?) : NetworkException(
        code = 1002,
        message = "Serializable error -> requestId: $message"
    )

    data class UnknownException(override val cause: Throwable? = null) : NetworkException(
        code = 0,
        message = "Unknown error"
    )
    data class HttpException(
        val httpCode: Int,
        val body: String = "Not Found",
        override val cause: Throwable? = null
    ) : NetworkException(
        code = httpCode,
        message = "HTTP error -> code: $httpCode, body: $body"
    )
}

sealed class ApiResponse<out T, out E> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<E> : ApiResponse<Nothing, E>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpError<E>(val code: Int, val errorBody: E?) : Error<E>()

        /**
         * Represent IOExceptions and connectivity issues.
         */
        object NetworkError : Error<Nothing>()

        /**
         * Represent SerializationExceptions.
         */
        object SerializationError : Error<Nothing>()
    }
}