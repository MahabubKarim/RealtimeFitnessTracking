package com.mmk.wearfitness.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*

@Composable
fun WearFitnessTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = wearColorPalette,
        typography = wearTypography,
        content = content
    )
}

private val wearColorPalette = Colors(
    primary = Color(0xFF4285F4),
    primaryVariant = Color(0xFF3367D6),
    secondary = Color(0xFF34A853),
    secondaryVariant = Color(0xFF1E8E3E),
    error = Color(0xFFEA4335),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onError = Color.White
)

private val wearTypography = androidx.wear.compose.material.Typography(
    display1 = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp
    ),
    display2 = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp
    ),
    title1 = androidx.compose.ui.text.TextStyle(
        fontSize = 12.sp
    ),
    title2 = androidx.compose.ui.text.TextStyle(
        fontSize = 10.sp
    ),
    body1 = androidx.compose.ui.text.TextStyle(
        fontSize = 8.sp
    ),
    caption1 = androidx.compose.ui.text.TextStyle(
        fontSize = 6.sp
    )
)