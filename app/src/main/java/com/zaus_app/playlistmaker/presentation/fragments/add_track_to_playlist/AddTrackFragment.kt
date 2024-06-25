package com.zaus_app.playlistmaker.presentation.fragments.add_track_to_playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.FragmentAddTrackBinding
import com.zaus_app.playlistmaker.databinding.FragmentMainBinding
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.presentation.MainActivity
import com.zaus_app.playlistmaker.presentation.fragments.favorites_fragment.FavoritesViewModel
import com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment.PlaylistsState
import com.zaus_app.playlistmaker.presentation.rv_adapter.AddTrackAdapter
import com.zaus_app.playlistmaker.presentation.rv_adapter.TrackAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTrackFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddTrackBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTrackViewModel by viewModel()
    private val playlistAdapter = AddTrackAdapter { playlist ->
        val track = arguments?.get("track") as Track
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = viewModel.getPlaylist(playlist)
                if (result.tracks.contains(track.trackId))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Трек уже добавлен в плейлист ${playlist.playlistName}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                else {
                    viewModel.updatePlaylist(track, playlist)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Добавлено в плейлист ${playlist.playlistName}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        binding.newPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_addTrackFragment_to_newPlaylistFragment)
        }
        viewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is PlaylistsState.Content -> {
                    playlistAdapter.submitList(it.playList)
                }
                is PlaylistsState.Empty -> {
                    playlistAdapter.submitList(it.playList)
                }
            }
        }

    }

    private fun setUpAdapter() {
        binding.PlaylistsRecycler.apply {
            adapter = playlistAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}