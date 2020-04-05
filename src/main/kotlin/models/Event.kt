package models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("message")
    val message: Message,
    @SerialName("replyToken")
    val replyToken: String,
    @SerialName("source")
    val source: Source,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("type")
    val type: String,
    @SerialName("mode")
    val mode: String
)
