package org.example.currencyapp.data.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime
import org.example.currencyapp.domain.PreferencesRepository

class PreferencesImpl(private val settings: Settings): PreferencesRepository {

    companion object {
        const val TIMESTAMP_KEY = "lastUpdated"
    }

    private val flowSettings: FlowSettings = (settings as ObservableSettings).toFlowSettings()

    override suspend fun saveLastUpdated(lastUpdated: String) {
        flowSettings.putLong(
            key = TIMESTAMP_KEY,
            value = Instant.parse(lastUpdated).toEpochMilliseconds()
        )
    }

    override suspend fun isDataFresh(currentTimestamp: Long): Boolean {
        val savedTimestamp =  flowSettings.getLong(
            key = TIMESTAMP_KEY,
           defaultValue = 0L
        )

        return if(savedTimestamp != 0L) {
            val currentInstant = Instant.fromEpochMilliseconds(currentTimestamp)
            val savedInstant = Instant.fromEpochMilliseconds(savedTimestamp)

            val currentDataTime = currentInstant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
            val savedDataTime = savedInstant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())

            val daysDifference = currentDataTime.date.dayOfYear - savedDataTime.date.dayOfYear

            daysDifference < 1

        } else false
    }
}