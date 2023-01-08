package com.caesar84mx.swensonforecast.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caesar84mx.swensonforecast.R

val sfDisplay = FontFamily(
    Font(
        resId = R.font.sf_pro_display_thin,
        weight = FontWeight.W100,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.sf_pro_display_regular,
        weight = FontWeight.W400,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.sf_pro_display_medium,
        weight = FontWeight.W500,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.sf_pro_display_semibold,
        weight = FontWeight.W600,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.sf_pro_display_bold,
        weight = FontWeight.W700,
        style = FontStyle.Normal
    )
)

val Typography = Typography(
    h2 = TextStyle(
        fontFamily = sfDisplay,
        fontWeight = FontWeight.W700,
        fontSize = 56.sp
    ),
    h4 = TextStyle(
        fontFamily = sfDisplay,
        fontWeight = FontWeight.W700,
        fontSize = 32.sp,
        lineHeight = 38.19.sp
    ),
    body1 = TextStyle(
        fontFamily = sfDisplay,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = sfDisplay,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 19.09.sp
    ),
    caption = TextStyle(
        fontFamily = sfDisplay,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 14.32.sp
    )
)