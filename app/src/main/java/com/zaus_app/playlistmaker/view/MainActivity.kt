package com.zaus_app.playlistmaker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

    fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, "fragment")
            .addToBackStack(null)
            .commit()
    }
}