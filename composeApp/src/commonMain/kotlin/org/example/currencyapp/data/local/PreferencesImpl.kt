package org.example.currencyapp.data.local

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import org.example.currencyapp.domain.PreferencesRepository
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

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
        val savedTimestamp = flowSettings.getLong(
            key = TIMESTAMP_KEY,
            defaultValue = 0L
        )

        return if (savedTimestamp != 0L) {
            val currentInstant = Instant.fromEpochMilliseconds(currentTimestamp)
            val savedInstant = Instant.fromEpochMilliseconds(savedTimestamp)

            // Converter os Instantes para LocalDateTimes, no fuso horário local
            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            // Calcular a diferença de dias entre as duas datas
            val daysDifference = currentDateTime.date.daysUntil(savedDateTime.date)

            println("Is data fresh??? --------------")
            println("daysDifference ------- $daysDifference")
            println("currentTimestamp ----- $currentTimestamp")
            println("currentDateTime.date.dayOfYear ----- ${currentDateTime.date.dayOfYear}")
            println("savedDateTime.date.dayOfYear ----- ${savedDateTime.date.dayOfYear}")

            // Retorna verdadeiro se a diferença for menor que 1 dia
            daysDifference < 1
        } else false
    }
}