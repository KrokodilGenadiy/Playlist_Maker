package com.zaus_app.playlistmaker.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.zaus_app.playlistmaker.databinding.FragmentMediaBinding
import com.zaus_app.playlistmaker.presentation.vp_adapter.MediaPagerAdapter


class MediaFragment : Fragment() {
    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = MediaPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = FAVORITES
                1 -> tab.text = PLAYLISTS
            }
        }
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        tabMediator.detach()
    }

    companion object {
        const val FAVORITES = "Избранные треки"
        const val PLAYLISTS = "Плейлисты"
    }

}