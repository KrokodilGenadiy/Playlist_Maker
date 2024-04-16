package com.zaus_app.playlistmaker.domain.di.modules

import android.content.Context
import com.zaus_app.playlistmaker.data.implementations.PreferenceProviderImpl
import com.zaus_app.playlistmaker.data.implementations.RemoteRepositoryImpl
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import com.zaus_app.playlistmaker.domain.repositrories.RemoteRepository

import org.koin.dsl.module

val repositoryModule = module {

    single<RemoteRepository> {
        RemoteRepositoryImpl(get())
    }

}