package com.zaus_app.playlistmaker.domain.di.modules

import com.zaus_app.playlistmaker.domain.usecase.RemoteUseCase
import com.zaus_app.playlistmaker.data.implementations.RemoteUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<RemoteUseCase> {
        RemoteUseCaseImpl(get())
    }
}