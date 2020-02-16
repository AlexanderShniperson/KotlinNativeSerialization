import Serializer

fun main() {
    val serializer = Serializer()
    val data = Serializer.SomeData("test")
    val encoded = serializer.serialize(data)
    val decoded = serializer.deserialize(serializer.getMessageId(data)!!, encoded!!)
    println(">>> encoded len=${encoded.size} equals=${decoded == data}")
}
