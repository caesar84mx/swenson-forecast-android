package com.caesar84mx.swensonforecast.data.networking.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.annotation.AnnotationRetention.RUNTIME

class LocalDateTimeAdapter: JsonAdapter<LocalDateTime>() {
    private val formatter = DateTimeFormatter.ofPattern(CURRENT_FORMAT)

    companion object {
        const val CURRENT_FORMAT = "yyyy-MM-dd H:mm"
    }

    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        value?.let { writer.value(value.format(formatter)) }
    }

    override fun fromJson(reader: JsonReader): LocalDateTime? {
        val timeString = reader.readJsonValue() as? String
        return LocalDateTime.parse(timeString, formatter)
    }
}