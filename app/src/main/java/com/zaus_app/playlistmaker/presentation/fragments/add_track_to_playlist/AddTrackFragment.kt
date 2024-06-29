package com.zaus_app.playlistmaker.presentation.fragments.add_track_to_playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.FragmentAddTrackBinding
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment.PlaylistsState
import com.zaus_app.playlistmaker.presentation.rv_adapter.AddTrackAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTrackFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddTrackBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddTrackViewModel by viewModel()
    private val playlistAdapter = AddTrackAdapter { playlist, position ->
        val track = arguments?.get("track") as Track
        viewModel.updatePlaylist(track,playlist,position)
    }

    private fun updateItem(position: Int) {
        val item = playlistAdapter.currentList[position]
        item.tracksCount += 1
        playlistAdapter.submitList(playlistAdapter.currentList)
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
        viewModel.observeAddTrackMessage().observe(viewLifecycleOwner) { addTrackUiModel ->
            if (addTrackUiModel.isAdded) {
                Toast.makeText(
                    requireContext(),
                    "Добавлено в плейлист ${addTrackUiModel.playlist.playlistName}",
                    Toast.LENGTH_LONG
                ).show()
                updateItem(addTrackUiModel.playlistPosition)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Трек уже добавлен в плейлист ${addTrackUiModel.playlist.playlistName}",
                    Toast.LENGTH_LONG
                ).show()
            }
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