package com.example.movieexplorer.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(json: String): List<String> {
        return if (json.isEmpty()) {
                emptyList()
            } else {
               gson.fromJson(json, object : TypeToken<List<String>>() {}.type)
            }
    }
}