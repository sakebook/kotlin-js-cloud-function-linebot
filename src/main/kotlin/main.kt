external val exports: dynamic
external fun require(module: String): dynamic

@JsModule("firebase-functions")
@JsNonModule
external object functions {
    val https: dynamic
}

fun main() {
    println("Hello Kotlin/JS!")
    exports.message = functions.https.onRequest { req, res ->
        console.log("message")
        res.status(200).send("Hello World!!")
    }
}
