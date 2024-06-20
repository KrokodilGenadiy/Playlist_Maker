package com.zaus_app.playlistmaker.domain.di.modules

import androidx.room.Room
import com.zaus_app.playlistmaker.data.db.FavoritesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            FavoritesDatabase::class.java,
            "db1FavoritesTracks.db"
        ).build()
    }
}