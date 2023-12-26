package com.zaus_app.playlistmaker.domain

import android.content.Context
import com.zaus_app.playlistmaker.domain.modules.DomainModule
import com.zaus_app.playlistmaker.domain.modules.RemoteModule
import com.zaus_app.playlistmaker.view.fragments.search_fragment.SearchViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, RemoteModule::class])
interface AppComponent {
    fun inject(viewModel: SearchViewModel)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): AppComponent
    }
}