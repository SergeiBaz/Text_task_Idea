package com.example.product.internal.storage

import androidx.room.TypeConverter

internal class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString(",")
    }
}