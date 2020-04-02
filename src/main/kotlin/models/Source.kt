package models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    @SerialName("type")
    val type: String,
    @SerialName("userId")
    val userId: String
)
