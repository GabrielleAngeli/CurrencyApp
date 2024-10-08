package org.example.currencyapp.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import org.example.currencyapp.data.remote.api.CurrencyApiServiceImpl

class HomeScreen: Screen{

    @Composable
    override fun Content() {
        LaunchedEffect(Unit){
            CurrencyApiServiceImpl().getLatestExchangeRates()
        }
    }
}