package br.com.souzabrunoj.storekmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform