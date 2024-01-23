package com.zaus_app.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var preferenceProvider: PreferenceProvider
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                preferenceProvider.saveDefaultTheme(darkThemeEnabled)
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                preferenceProvider.saveDefaultTheme(darkThemeEnabled)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
