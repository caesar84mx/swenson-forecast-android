package com.caesar84mx.swensonforecast.data.networking.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter: JsonAdapter<LocalDate>() {
    private val formatter = DateTimeFormatter.ofPattern(CURRENT_FORMAT)

    companion object {
        const val CURRENT_FORMAT = "yyyy-MM-dd"
    }

    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        value?.let { writer.value(value.format(formatter)) }
    }

    override fun fromJson(reader: JsonReader): LocalDate? {
        val timeString = reader.readJsonValue() as? String
        return LocalDate.parse(timeString, formatter)
    }
}