package org.example.currencyapp.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.currencyapp.domain.CurrencyApiService
import org.example.currencyapp.domain.model.Currency
import org.example.currencyapp.domain.model.RequestState

class CurrencyApiServiceImpl: CurrencyApiService {
    companion object {
        const val ENDPOINT = "https://api.currencyapi.com/v3/latest"
        const val API_KEY = "cur_live_LXyzUu0JnfMeW7OiKEzX6hDIurDSObsitE9tm9X5"
    }

    private val httpClient = HttpClient{
        install(ContentNegotiation) {
            json( Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
    }


    override fun getLatestExchangeRates(): RequestState<List<Currency>> {
        TODO("Not yet implemented")
    }
}