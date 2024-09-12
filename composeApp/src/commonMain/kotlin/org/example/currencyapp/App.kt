package org.example.currencyapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.example.currencyapp.di.initializeKoin
import org.example.currencyapp.ui.theme.DarkColors
import org.example.currencyapp.ui.theme.LightColors
import org.example.currencyapp.presentation.screen.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val colors = if(!isSystemInDarkTheme()) LightColors else DarkColors
    initializeKoin()
    MaterialTheme(colorScheme = colors) {
        Navigator(HomeScreen())
    }
}