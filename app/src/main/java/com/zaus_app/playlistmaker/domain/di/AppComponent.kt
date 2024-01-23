package com.zaus_app.playlistmaker.domain.di

import android.app.Activity
import android.app.Application
import android.content.Context
import com.zaus_app.playlistmaker.domain.di.modules.DomainModule
import com.zaus_app.playlistmaker.domain.di.modules.RemoteModule
import com.zaus_app.playlistmaker.view.fragments.search_fragment.SearchViewModel
import com.zaus_app.playlistmaker.view.fragments.settings_fragment.SettingsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, RemoteModule::class])
interface AppComponent {
    fun inject(viewModel: SearchViewModel)
    fun inject(viewModel: SettingsViewModel)
    fun inject(app: Application)
    fun inject(activity: Activity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): AppComponent
    }
}