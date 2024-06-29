package com.zaus_app.playlistmaker.domain.di.modules

import com.zaus_app.playlistmaker.data.implementations.RemoteRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.interactors.AudioPlayerInteractorImpl
import com.zaus_app.playlistmaker.data.implementations.interactors.FavoritesInteractorImpl
import com.zaus_app.playlistmaker.data.implementations.interactors.PlaylistInteractorImpl
import com.zaus_app.playlistmaker.data.implementations.interactors.SettingInteractorImpl
import com.zaus_app.playlistmaker.domain.interactors.AudioPlayerInteractor
import com.zaus_app.playlistmaker.domain.interactors.FavoritesInteractor
import com.zaus_app.playlistmaker.domain.interactors.PlaylistInteractor
import com.zaus_app.playlistmaker.domain.interactors.SettingsInteractor
import com.zaus_app.playlistmaker.domain.repositrories.RemoteRepository
import org.koin.dsl.module

val interactorModule = module {

    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get())
    }

    factory<AudioPlayerInteractor> {
        AudioPlayerInteractorImpl(get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    factory<SettingsInteractor> {
        SettingInteractorImpl(get())
    }
}