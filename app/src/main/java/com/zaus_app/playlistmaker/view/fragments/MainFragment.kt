package com.zaus_app.playlistmaker.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zaus_app.playlistmaker.databinding.FragmentMainBinding
import com.zaus_app.playlistmaker.view.MainActivity
import com.zaus_app.playlistmaker.view.fragments.search_fragment.SearchFragment
import com.zaus_app.playlistmaker.view.fragments.settings_fragment.SettingsFragment


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
                (requireActivity() as MainActivity).launchFragment(SearchFragment(),"Search")
            }
            mediaContainer.setOnClickListener {
                (requireActivity() as MainActivity).launchFragment(MediaFragment(),"Media")
            }
            settingsContainer.setOnClickListener {
                (requireActivity() as MainActivity).launchFragment(SettingsFragment(),"Settings")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}