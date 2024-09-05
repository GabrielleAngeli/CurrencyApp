package org.example.currencyapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.currencyapp.data.ui.theme.DarkColors
import org.example.currencyapp.data.ui.theme.LightColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val colors = if(!isSystemInDarkTheme()) LightColors else DarkColors


    MaterialTheme(colorScheme = colors) {

    }
}