package models

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializable
enum class Type {
    text,
    image,
    video,
    audio,
    file,
    location,
    sticker,
    ;

    fun kind(): String {
        return when (this) {
            text -> "テキスト"
            image -> "画像"
            video -> "動画"
            audio -> "音声"
            file -> "ファイル"
            location -> "位置情報"
            sticker -> "スタンプ"
        }
    }
}

@Serializer(forClass = Type::class)
object TypeSerializer : KSerializer<Type> {
    override val descriptor: SerialDescriptor = StringDescriptor

    override fun deserialize(decoder: Decoder): Type {
        val type = decoder.decodeString()
        return Type.valueOf(type)
    }

    override fun serialize(encoder: Encoder, obj: Type) {
        encoder.encodeString(obj.name)
    }
}
