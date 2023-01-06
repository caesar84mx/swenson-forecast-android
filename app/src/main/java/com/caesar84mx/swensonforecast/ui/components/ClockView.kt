package com.caesar84mx.swensonforecast.ui.components

import android.util.TypedValue
import android.widget.TextClock
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ClockView(
    spSize: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            TextClock(context).apply {
                format12Hour?.let { this.format12Hour = "hh:mm a" }
                timeZone?.let { this.timeZone = it }
                setTextSize(TypedValue.COMPLEX_UNIT_SP, spSize)
                setTextColor(color.toArgb())
            }
        },
        modifier = modifier.padding(5.dp),
    )
}