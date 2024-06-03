package com.zaus_app.playlistmaker.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.zaus_app.playlistmaker.App
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.ActivityMainBinding
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.presentation.fragments.MainFragment
import com.zaus_app.playlistmaker.presentation.fragments.MediaFragment
import com.zaus_app.playlistmaker.presentation.fragments.player_fragment.PlayerFragment
import com.zaus_app.playlistmaker.presentation.fragments.search_fragment.SearchFragment
import com.zaus_app.playlistmaker.presentation.fragments.settings_fragment.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.instance.switchTheme()
        initNavigation()
    }

    fun launchFragment(fragment: Fragment, tag: String) {
        currentFragmentTag = tag
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun launchPlayerFragment(track: Track) {
        val bundle = Bundle()
        bundle.putParcelable("track", track)
        val fragment = checkFragmentExistence("player") ?: PlayerFragment()
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, "player")
            .addToBackStack("player")
            .commit()
    }

    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_placeholder) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.playerFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                    binding.divider.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                    binding.divider.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        currentFragmentTag = null
    }
}