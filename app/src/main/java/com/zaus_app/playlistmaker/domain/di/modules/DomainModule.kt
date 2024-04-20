package com.zaus_app.playlistmaker.domain.di.modules

import android.media.MediaPlayer
import com.zaus_app.playlistmaker.data.implementations.PreferenceProviderImpl
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import org.koin.dsl.module

val domainModule = module {

    single<PreferenceProvider> {
        PreferenceProviderImpl(get())
    }

    factory<MediaPlayer> {
        MediaPlayer()
    }

}