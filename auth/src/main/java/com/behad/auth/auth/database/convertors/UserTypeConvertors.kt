package com.behad.auth.auth.database.convertors

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UserTypeConvertors {

    private val listStringJsonType: Type = object : TypeToken<List<String>?>() {}.type
    private val anyJsonType: Type = object : TypeToken<Any?>() {}.type
    private val gson = Gson()

    @TypeConverter
    fun listStringToString(list: List<String>?): String? = gson.toJson(list, listStringJsonType)

    @TypeConverter
    fun stringToListString(string: String?): List<String>? =
        gson.fromJson(string, listStringJsonType)

    @TypeConverter
    fun anyToString(any: Any?): String? = gson.toJson(any, anyJsonType)

    @TypeConverter
    fun stringToAny(string: String?): Any? = gson.fromJson(string, anyJsonType)
}
