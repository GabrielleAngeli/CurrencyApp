package org.example.currencyapp.domain

interface PreferencesRepository {
    suspend fun saveLastUpdated(lastUpdated: String)
    suspend fun isDataFresh(currentTimestamp: Long): Boolean
}