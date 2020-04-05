package models

data class Reply(
    val replyToken: String,
    val messages: List<ReplyMessage>
)
