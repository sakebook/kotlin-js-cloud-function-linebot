import kotlinx.serialization.json.Json
import models.WebhookEvent

external val exports: dynamic
external fun require(module: String): dynamic

@JsModule("firebase-functions")
@JsNonModule
external object functions {
    val https: dynamic
}

fun main() {
    println("Hello Kotlin/JS!!!")
    exports.message = functions.https.onRequest { req, res ->
        console.log("message")
        val json = JSON.stringify(req.body)
        console.log("json ${json}")
        val events = Json.parse(WebhookEvent.serializer(), json)
        res.status(200).send("Hello World!!!")
    }
}
