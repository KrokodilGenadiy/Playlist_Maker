package com.zaus_app.playlistmaker.domain.di.modules

import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import com.zaus_app.playlistmaker.domain.usecase.RemoteUseCase
import com.zaus_app.playlistmaker.presentation.fragments.favorites_fragment.FavoritesViewModel
import com.zaus_app.playlistmaker.presentation.fragments.player_fragment.PlayerViewModel
import com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment.PlaylistsViewModel
import com.zaus_app.playlistmaker.presentation.fragments.search_fragment.SearchViewModel
import com.zaus_app.playlistmaker.presentation.fragments.settings_fragment.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<PlayerViewModel> {
        PlayerViewModel(get())
    }

    viewModel<SearchViewModel> {
        SearchViewModel(get<RemoteUseCase>(), get<PreferenceProvider>())
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(get<PreferenceProvider>())
    }

    viewModel<FavoritesViewModel> {
        FavoritesViewModel()
    }

    viewModel<PlaylistsViewModel> {
        PlaylistsViewModel()
    }
}