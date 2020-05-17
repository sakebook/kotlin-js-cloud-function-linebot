package models

import kotlinx.serialization.*

@Serializable
enum class Type {
    TEXT,
    IMAGE,
    VIDEO,
    AUDIO,
    FILE,
    LOCATION,
    STICKER,
    ;

    fun kind(): String {
        return when (this) {
            TEXT -> "テキスト"
            IMAGE -> "画像"
            VIDEO -> "動画"
            AUDIO -> "音声"
            FILE -> "ファイル"
            LOCATION -> "位置情報"
            STICKER -> "スタンプ"
        }
    }
}

@Serializer(forClass = Type::class)
object TypeSerializer : KSerializer<Type> {
    override val descriptor: SerialDescriptor = PrimitiveDescriptor("type", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Type {
        val type = decoder.decodeString()
        return Type.valueOf(type.toUpperCase())
    }

    override fun serialize(encoder: Encoder, obj: Type) {
        encoder.encodeString(obj.name.toLowerCase())
    }
}
