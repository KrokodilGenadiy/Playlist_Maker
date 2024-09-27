package com.zaus_app.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.zaus_app.playlistmaker.domain.di.modules.dataModule
import com.zaus_app.playlistmaker.domain.di.modules.domainModule
import com.zaus_app.playlistmaker.domain.di.modules.interactorModule
import com.zaus_app.playlistmaker.domain.di.modules.remoteModule
import com.zaus_app.playlistmaker.domain.di.modules.repositoryModule
import com.zaus_app.playlistmaker.domain.di.modules.useCaseModule
import com.zaus_app.playlistmaker.domain.di.modules.viewModelModule
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    private val preferenceProvider: PreferenceProvider by inject()
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@App)
            modules(domainModule, repositoryModule, useCaseModule, remoteModule, viewModelModule, dataModule,
                interactorModule)
        }
    }

    fun switchTheme(theme: Boolean? = null) {
        val defaultTheme = theme ?: preferenceProvider.getDefaultTheme()
        AppCompatDelegate.setDefaultNightMode(
            if (defaultTheme) {
                preferenceProvider.saveDefaultTheme(defaultTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                preferenceProvider.saveDefaultTheme(defaultTheme)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
