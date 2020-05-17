package models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebHookEvent(
    @SerialName("events")
    val events: List<Event>,
    @SerialName("destination")
    val destination: String
)
