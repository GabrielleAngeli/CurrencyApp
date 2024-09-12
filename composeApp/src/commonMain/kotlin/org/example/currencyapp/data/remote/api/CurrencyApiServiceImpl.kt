package org.example.currencyapp.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.currencyapp.domain.CurrencyApiService
import org.example.currencyapp.domain.PreferencesRepository
import org.example.currencyapp.domain.model.ApiResponse
import org.example.currencyapp.domain.model.Currency
import org.example.currencyapp.domain.model.RequestState

class CurrencyApiServiceImpl(
    private val preferences: PreferencesRepository
): CurrencyApiService {
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

        install(DefaultRequest){
            headers{
                append("apikey", API_KEY)
            }
        }
    }


    override suspend fun getLatestExchangeRates(): RequestState<List<Currency>> {
        return try {
            val response = httpClient.get(ENDPOINT)
            if(response.status.value == 200) {
                println("API RESPoNSE: ${response.body<String>()}")
                val apiResponse = Json.decodeFromString<ApiResponse>(response.body())

                //Persist a timestamp
                val lastUpdated = apiResponse.meta.lastUpdatedAt
                preferences.saveLastUpdated(lastUpdated)

                RequestState.Success(data = apiResponse.data.values.toList())
            } else {
                RequestState.Error(message = "Http Error Code: ${response.status}")
            }
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}