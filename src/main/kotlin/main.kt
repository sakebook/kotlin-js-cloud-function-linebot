external val exports: dynamic
external fun require(module: String): dynamic

val functions = require("firebase-functions")

fun main() {
    println("Hello Kotlin/JS!")
    exports.message = functions.https.onRequest { req, res ->
        console.log("message")
        res.status(200).send("Hello World!")
    }
}
