package org.example.currencyapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import org.example.currencyapp.data.remote.api.CurrencyApiServiceImpl
import org.example.currencyapp.presentation.component.HomeHeader
import org.example.currencyapp.ui.theme.surfaceColor

class HomeScreen: Screen{

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        val rateStatus by viewModel.rateStatus

       Column(modifier = Modifier
           .fillMaxWidth()
           .background(surfaceColor)
       ) {
           HomeHeader(
               status = rateStatus,
               onRatesRefresh = {
                   viewModel.sendEvent(
                       HomeUiEvent.RefreshRates
                   )
               }
           )
       }
    }
}