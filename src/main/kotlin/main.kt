import kotlinx.serialization.json.*
import models.*
import wrapper.Functions

external val exports: dynamic

fun main() {
    exports.message = Functions.https.onRequest { req, res ->
        val json = JSON.stringify(req.body)
        console.log("json $json")
        val webhookEvent = Json.nonstrict.parse(WebhookEvent.serializer(), json)
        val token = webhookEvent.events.first().replyToken
        Axios.post<Any, JsonObject>(url = "https://api.line.me/v2/bot/message/reply", data = Reply(token, listOf(
            ReplyMessage(text = createText(webhookEvent.events.first().message))
        )), config = Axios.defaults.apply {
            this.method = "POST"
            this.responseType = "JSON"
            this.headers = js {
                this.Authorization =
                    "Bearer ${js("process.env.CHANNEL_ACCESS_TOKEN")}"
            } as? Any
        }).then {
            console.log("then ${JSON.stringify(it)}")
        }.catch {
            console.log("catch ${JSON.stringify(it)}")
        }
        res.status(200).send("Success!!")
    }
}

private fun createText(message: Message): String {
    val text = when (val type = message.type) {
        Type.TEXT -> message.text
        Type.IMAGE,
        Type.VIDEO,
        Type.AUDIO,
        Type.FILE,
        Type.LOCATION,
        Type.STICKER -> "${type.kind()}は対応していません"
    }
    return "[オウム返し]\n$text"
}
