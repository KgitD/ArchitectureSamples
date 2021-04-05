package com.ch999.android.paging3.ui.style

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ch999.android.paging3.ui.theme.Black33
import com.ch999.android.paging3.ui.theme.DarkerGray

fun titleTextStyle(): TextStyle {
    return TextStyle(
        color = Black33, fontSize = 16.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Serif, fontSynthesis = FontSynthesis.Style
    )
}

fun contentTextStyle(): TextStyle {
    return TextStyle(
        color = DarkerGray, fontSize = 14.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.SansSerif, fontSynthesis = FontSynthesis.Style
    )
}