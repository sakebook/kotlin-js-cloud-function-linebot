import kotlinx.serialization.json.*
import models.*
import wrapper.Request
import wrapper.Response
import kotlin.js.Promise

external val exports: dynamic

fun main() {
    exports.message = ::message
}

fun message(req: Request, res: Response) {
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

private fun parseRequest(req: Request): WebHookEvent {
    val json = JSON.stringify(req.body)
    console.log("json $json")
    return Json.parse(WebHookEvent.serializer(), json)
}

private fun createReply(webHookEvent: WebHookEvent): Reply {
    val token = webHookEvent.events.first().replyToken
    return Reply(token, listOf(
        ReplyMessage(text = createText(webHookEvent.events.first().message))
    ))
}

private fun postReply(reply: Reply): Promise<JsonObject> {
    val axios = Axios.create(object : AxiosRequestConfig {
        override var method: String = "POST"
        override var responseType: String = "json"
        override var headers: Any? = kotlinext.js.js {
            this.Authorization =
                "Bearer ${js("process.env.CHANNEL_ACCESS_TOKEN")}"
        } as? Any
    })
    return axios.post<Unit, JsonObject>(url = "https://api.line.me/v2/bot/message/reply", data = reply)
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
