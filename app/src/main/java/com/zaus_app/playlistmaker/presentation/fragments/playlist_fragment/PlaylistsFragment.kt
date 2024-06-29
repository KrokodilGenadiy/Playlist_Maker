package com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.FragmentPlaylistsBinding
import com.zaus_app.playlistmaker.presentation.MainActivity
import com.zaus_app.playlistmaker.presentation.rv_adapter.PlaylistAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {
    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlaylistsViewModel by viewModel()
    private val playlistAdapter = PlaylistAdapter { playlist ->
        (requireActivity() as MainActivity).launchPlaylistDetailsFragment(playlist)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newPlaylist()
        setUpAdapter()
        updateVisibility()
        viewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is PlaylistsState.Content -> {
                    playlistAdapter.submitList(it.playList)
                    binding.placeholder.root.visibility = View.GONE
                }
                is PlaylistsState.Empty -> {
                    binding.placeholder.root.visibility = View.VISIBLE
                    playlistAdapter.submitList(it.playList)
                }
            }

        }
    }

    fun updateVisibility() {
        binding.placeholder.root.isVisible = playlistAdapter.currentList.isEmpty()
    }

    fun newPlaylist() {
        binding.newPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_mediaFragment_to_newPlaylistFragment)
        }
    }

    fun setUpAdapter() {
        binding.PlaylistsRecycler.apply {
            adapter = playlistAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = PlaylistsFragment()
    }
}