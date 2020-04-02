package models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("id")
    val id: String,
    @SerialName("type")
    val type: String,
    @SerialName("packageId")
    val packageId: String? = null,
    @SerialName("stickerId")
    val stickerId: String? = null,
    @SerialName("text")
    val text: String? = null
)
