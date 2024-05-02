package ir.dorantech.kmmreference

import ir.dorantech.kmmreference.CommonMethods.stringToJson
import ir.dorantech.kmmreference.CommonMethods.valueFromName
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject

object AndroidMethods {
    fun String.toJsonAndroid() = JSONObject(this)
    fun JSONObject.getValueAndroid(key: String) = optString(key)

    fun String.toJsonCommon() = stringToJson(this)
    fun JsonObject.getValueCommon(key: String) = valueFromName(this, key)

}