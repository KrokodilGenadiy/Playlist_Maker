package com.zaus_app.playlistmaker.presentation.fragments.settings_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider

class SettingsViewModel(private val preferenceProviderImpl: PreferenceProvider): ViewModel() {
    val themeLiveData = MutableLiveData<Boolean>()
    fun observeTheme(): LiveData<Boolean> = themeLiveData
    fun getThemeStatus() {
        themeLiveData.postValue(preferenceProviderImpl.getDefaultTheme())
    }
}