package com.zaus_app.playlistmaker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zaus_app.playlistmaker.App
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.domain.preferences.PreferenceProvider
import com.zaus_app.playlistmaker.view.fragments.MainFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var preferenceProvider: PreferenceProvider
    private var currentFragmentTag: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.switchTheme(preferenceProvider.getDefaultTheme())
        if (savedInstanceState == null) {
            launchFragment(MainFragment(), "Main")
        } else {
            currentFragmentTag = savedInstanceState.getString("currentFragmentTag")
            val currentFragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
            if (currentFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_placeholder, currentFragment, currentFragmentTag)
                    .commit()
            } else {
                launchFragment(MainFragment(), "Main")
            }
        }
    }

    fun launchFragment(fragment: Fragment, tag: String) {
        currentFragmentTag = tag
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentFragmentTag", currentFragmentTag)
    }

    override fun onDestroy() {
        super.onDestroy()
        currentFragmentTag = null
    }
}