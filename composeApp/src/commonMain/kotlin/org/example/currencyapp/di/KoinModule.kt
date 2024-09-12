package org.example.currencyapp.di

import com.russhwolf.settings.Settings
import org.example.currencyapp.data.local.PreferencesImpl
import org.example.currencyapp.data.remote.api.CurrencyApiServiceImpl
import org.example.currencyapp.domain.CurrencyApiService
import org.example.currencyapp.domain.PreferencesRepository
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { Settings() }
    single<PreferencesRepository> { PreferencesImpl(settings = get()) }
    single<CurrencyApiService> { CurrencyApiServiceImpl(preferences = get()) }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}