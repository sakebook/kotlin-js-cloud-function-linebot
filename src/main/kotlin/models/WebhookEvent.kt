package models
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebhookEvent(
    @SerialName("events")
    val events: List<Event>
)
