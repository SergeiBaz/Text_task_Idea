package com.example.product.until

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Должно быть в core каком нибудь, но его было лень создавать
 */

internal fun Long.toDateString(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.format(Date(this))
}

internal fun String.toMilliseconds(): Long {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()
    return try {
        val date = dateFormat.parse(this) ?: throw ParseException("Invalid date format", 0)
        date.time
    } catch (e: ParseException) {
        e.printStackTrace()
        0L
    }
}