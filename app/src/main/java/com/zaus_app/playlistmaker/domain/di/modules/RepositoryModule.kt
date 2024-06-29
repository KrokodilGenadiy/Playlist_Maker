package com.zaus_app.playlistmaker.domain.di.modules

import com.zaus_app.playlistmaker.data.db.FavoritesDatabase
import com.zaus_app.playlistmaker.data.implementations.AudioPlayerRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.FavoritesDatabaseRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.ImageStorageRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.PlaylistRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.RemoteRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.SettingsRepositoryImpl
import com.zaus_app.playlistmaker.domain.repositrories.AudioPlayerRepository
import com.zaus_app.playlistmaker.domain.repositrories.FavoritesDatabaseRepository
import com.zaus_app.playlistmaker.domain.repositrories.ImageStorageRepository
import com.zaus_app.playlistmaker.domain.repositrories.PlaylistRepository
import com.zaus_app.playlistmaker.domain.repositrories.RemoteRepository
import com.zaus_app.playlistmaker.domain.repositrories.SettingsRepository
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.module

val repositoryModule = module {

    single<RemoteRepository> {
        RemoteRepositoryImpl(get())
    }

    factory<AudioPlayerRepository> {
        AudioPlayerRepositoryImpl(get())
    }

    single<FavoritesDatabaseRepository> {
        FavoritesDatabaseRepositoryImpl(get())
    }

    single<ImageStorageRepository> {
        ImageStorageRepositoryImpl(androidContext())
    }

    factory <PlaylistRepository>{
        PlaylistRepositoryImpl(get(), get(), get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(androidContext())
    }
}