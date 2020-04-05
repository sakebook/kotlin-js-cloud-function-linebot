package wrapper

@JsModule("firebase-functions")
@JsNonModule
@JsName("functions")
external object Functions {
    val https: Https
}

external interface Https {
    fun onRequest(func: (Request, Response) -> Unit)
}

external interface Request {
    val body: Any
}

external interface Response {
    fun status(code: Int): Response
    fun send(message: String): Response
}
