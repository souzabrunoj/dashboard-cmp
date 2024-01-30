package br.com.souzabrunoj.storekmp.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductRating(
    @SerialName("count")
    val count: Int,
    @SerialName("rate")
    val rate: Double
)