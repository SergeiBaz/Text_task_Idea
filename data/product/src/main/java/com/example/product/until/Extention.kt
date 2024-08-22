package com.example.product.until

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter
import java.time.Instant.ofEpochMilli
import java.time.ZoneId.systemDefault
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
internal fun Long.toDateString(): String {
    val date = ofEpochMilli(this)
        .atZone(systemDefault())
        .toLocalDate()
    return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}

@RequiresApi(Build.VERSION_CODES.O)
internal fun String.toMilliseconds(): Long {
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDate = LocalDate.parse(this, dateFormatter)
    return localDate.atStartOfDay(systemDefault()).toInstant().toEpochMilli()
}