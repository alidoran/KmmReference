package ir.dorantech.kmmreference.equevelents_tools

import ir.dorantech.kmmreference.CommonMethods
import org.json.JSONObject

object Json {
    fun jsonCompare() : Boolean{
        val jsonString = "{\"name\":\"Alice\",\"age\":25}"

        val androidOldJsonObject = JSONObject(jsonString)
        val androidOldValueFromJson = androidOldJsonObject.optString("name")

        val commonJsonObjectInCommonModule = CommonMethods.stringToJson(jsonString)
        val commonValueFromJsonInCommonModule = CommonMethods.valueFromName(commonJsonObjectInCommonModule, "name")

        return androidOldValueFromJson.equals(commonValueFromJsonInCommonModule)
    }
}