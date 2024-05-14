package ir.dorantech.kmmreference

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

object CommonMethods {
    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    fun stringToJson(message: String) = Json.decodeFromString<JsonObject>(message)
    fun valueFromName(jsonObject: JsonObject, key: String) =
        jsonObject[key]?.jsonPrimitive?.contentOrNull ?: ""
}