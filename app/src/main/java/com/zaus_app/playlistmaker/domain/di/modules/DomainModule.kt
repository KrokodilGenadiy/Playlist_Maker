package com.zaus_app.playlistmaker.domain.di.modules

import android.content.Context
import com.zaus_app.playlistmaker.data.api.TrackApi
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import com.zaus_app.playlistmaker.domain.repositroies.RemoteRepository
import com.zaus_app.playlistmaker.domain.repositroies.implementation.RemoteRepositoryImpl
import com.zaus_app.playlistmaker.domain.usecase.RemoteUseCase
import com.zaus_app.playlistmaker.domain.usecase.implementation.RemoteUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideRemoteUseCase(repository: RemoteRepository): RemoteUseCase = RemoteUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideRemoteRepository(trackApi: TrackApi): RemoteRepository = RemoteRepositoryImpl(trackApi)

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context) = PreferenceProvider(appContext)

}