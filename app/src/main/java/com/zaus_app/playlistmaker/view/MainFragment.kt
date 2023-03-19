package com.zaus_app.playlistmaker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchContainer.setOnClickListener {
                (requireActivity() as MainActivity).launchFragment(SearchFragment())
            }
            mediaContainer.setOnClickListener {
                (requireActivity() as MainActivity).launchFragment(MediaFragment())
            }
            settingsContainer.setOnClickListener {
                (requireActivity() as MainActivity).launchFragment(SettingsFragment())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}