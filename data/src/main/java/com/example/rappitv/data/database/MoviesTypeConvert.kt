package com.example.rappitv.data.database

import androidx.room.TypeConverter
import com.example.rappitv.domain.ResultX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoviesTypeConvert {
    var gson = Gson()

    @TypeConverter
    fun restaurantToString(movies: ResultX): String {
        return gson.toJson(movies)
    }

    @TypeConverter
    fun stringToRestaurant(data: String): ResultX {
        val listType = object : TypeToken<ResultX>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}