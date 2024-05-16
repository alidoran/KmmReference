package ir.dorantech.kmmreference

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SampleComplexDataClass(
    @SerialName("name")
    val name: String,
    val age: Int
):SerializableObject{
    override fun toJson(): String =
        Json.decodeFromString(name)


}

interface SerializableObject{
    fun toJson(): String
}