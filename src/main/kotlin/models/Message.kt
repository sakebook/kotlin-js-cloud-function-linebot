package models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("id")
    val id: String,
    @SerialName("type")
    @Serializable(with=TypeSerializer::class)
    val type: Type,
    @SerialName("text")
    val text: String? = null
)
