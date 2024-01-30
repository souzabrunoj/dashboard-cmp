package br.com.souzabrunoj.storekmp.data.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductRatingResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("rate")
    val rate: Double
)