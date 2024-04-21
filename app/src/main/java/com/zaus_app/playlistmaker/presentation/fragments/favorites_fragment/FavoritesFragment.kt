package com.zaus_app.playlistmaker.presentation.fragments.favorites_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zaus_app.playlistmaker.databinding.FragmentFavoritesBinding
import com.zaus_app.playlistmaker.presentation.fragments.player_fragment.PlayerViewModel
import com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment.PlaylistsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}