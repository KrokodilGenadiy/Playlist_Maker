package com.zaus_app.playlistmaker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaus_app.playlistmaker.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, MainFragment())
            .commit()
    }

    fun launchSettingsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, SettingsFragment(), "details")
            .addToBackStack("details")
            .commit()
    }
}