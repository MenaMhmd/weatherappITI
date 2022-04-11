package com.example.myapplication.model.local


import androidx.room.TypeConverter
import com.example.myapplication.model.entity.Modelweather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun toString(user: Modelweather): String {
        return Gson().toJson(user)
    }

    @TypeConverter
    fun fromString(value: String): Modelweather {
        var userType = object : TypeToken<Modelweather>() {}.type
        return Gson().fromJson<Modelweather>(value, userType)
    }
}