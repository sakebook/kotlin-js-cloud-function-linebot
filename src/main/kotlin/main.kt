import kotlinx.serialization.json.*
import models.*
import wrapper.Request
import wrapper.Response
import kotlin.js.Promise

external val exports: dynamic

fun main() {
    exports.message = { req: Request, res: Response ->
        val webhookEvent = parseRequest(req)
        val reply = createReply(webhookEvent)
        postReply(reply)
            .then {
                console.log("then ${JSON.stringify(it) { key: String, value: Any? ->
                    when (key) {
                        "request" -> ""
                        else -> value
                    }
                }}")
                res.status(200).send("Success!!")
            }.catch {
                console.log("catch ${JSON.stringify(it)}")
                res.status(400).send("Error!!")
            }
    }
}

private fun parseRequest(req: Request): WebhookEvent {
    val json = JSON.stringify(req.body)
    console.log("json $json")
    return Json.nonstrict.parse(WebhookEvent.serializer(), json)
}

private fun createReply(webhookEvent: WebhookEvent): Reply {
    val token = webhookEvent.events.first().replyToken
    return Reply(token, listOf(
        ReplyMessage(text = createText(webhookEvent.events.first().message))
    ))
}

private fun postReply(reply: Reply): Promise<JsonObject> {
    return Axios.post<Unit, JsonObject>(url = "https://api.line.me/v2/bot/message/reply", data = reply, config = Axios.defaults.apply {
        this.method = "POST"
        this.responseType = "JSON"
        this.headers = js {
            this.Authorization =
                "Bearer ${js("process.env.CHANNEL_ACCESS_TOKEN")}"
        } as? Any
    })
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
