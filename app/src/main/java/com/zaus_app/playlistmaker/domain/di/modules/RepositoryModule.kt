package com.zaus_app.playlistmaker.domain.di.modules

import com.zaus_app.playlistmaker.data.db.FavoritesDatabase
import com.zaus_app.playlistmaker.data.implementations.AudioPlayerRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.FavoritesDatabaseRepositoryImpl
import com.zaus_app.playlistmaker.data.implementations.RemoteRepositoryImpl
import com.zaus_app.playlistmaker.domain.repositrories.AudioPlayerRepository
import com.zaus_app.playlistmaker.domain.repositrories.FavoritesDatabaseRepository
import com.zaus_app.playlistmaker.domain.repositrories.RemoteRepository

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

}