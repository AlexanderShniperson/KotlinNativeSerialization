import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import kotlin.reflect.KClass

class Serializer {
    interface  MessageBase

    @Serializable
    data class SomeData(val some: String) : MessageBase

    class SerializerInfo<T : MessageBase>(
        val id: Int,
        val clazz: KClass<T>,
        val serializer: KSerializer<T>
    )

    val serializers = listOf(
        SerializerInfo(
            id = 1,
            clazz = SomeData::class,
            serializer = SomeData.serializer()
        )
    )

    fun getMessageId(message: MessageBase): Int? {
        return serializers.find { it.clazz == message::class }?.id
    }

    fun serialize(data: MessageBase): ByteArray? {
        return serializers.find { it.clazz == data::class }?.let {
            ProtoBuf.dump(it.serializer, data) // <-- Problem here type can't be inferred
        }
    }

    fun deserialize(msgId: Int, data: ByteArray): MessageBase? {
        return serializers.find { it.id == msgId }?.let {
            ProtoBuf.load(it.serializer, data)
        }
    }
}