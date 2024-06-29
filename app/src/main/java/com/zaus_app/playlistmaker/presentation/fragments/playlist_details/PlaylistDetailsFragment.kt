package com.zaus_app.playlistmaker.presentation.fragments.playlist_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.FragmentPlayerBinding
import com.zaus_app.playlistmaker.databinding.FragmentPlaylistDetailsBinding
import com.zaus_app.playlistmaker.databinding.FragmentPlaylistsBinding
import com.zaus_app.playlistmaker.domain.entities.Playlist
import com.zaus_app.playlistmaker.domain.entities.Track
import com.zaus_app.playlistmaker.presentation.MainActivity
import com.zaus_app.playlistmaker.presentation.rv_adapter.PlaylistTrackAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlaylistDetailsFragment : Fragment() {
    private var _binding: FragmentPlaylistDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaylistDetailsViewModel by viewModel()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetBehaviorMenu: BottomSheetBehavior<ConstraintLayout>

    private val trackClickListener = object : PlaylistTrackAdapter.OnItemClickListener {
        override fun click(track: Track) {
            (requireActivity() as MainActivity).launchPlayerFragment(track)
        }

        override fun onLongClick(track: Track,position: Int): Boolean  {
            showDialogForDeleteTrack(track,position)
            return true
        }
    }

    private val trackAdapter = PlaylistTrackAdapter(trackClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playlist = arguments?.get("playlist") as Playlist
        viewModel.playlist = flowOf(playlist)
        viewModel.getData()
        binding.rvListPlaylists.adapter = trackAdapter
        viewModel.observeState().observe(viewLifecycleOwner) {
            when (it) {
                is PlaylistDetailsState.Content -> showPlaylist(it)
                is PlaylistDetailsState.Delete ->{} //findNavController().navigateUp()
            }
        }

        binding.btnGoBack.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val bottomSheetContainerTrack = binding.bottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainerTrack)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        val bottomSheetContainerMenu = binding.bottomSheetMenu
        bottomSheetBehaviorMenu = BottomSheetBehavior.from(bottomSheetContainerMenu)
        bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehaviorMenu.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }
                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        binding.share.setOnClickListener {
            sharePlaylist()
        }

        binding.shareText.setOnClickListener {
            sharePlaylist()
            bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.menu.setOnClickListener {
            bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.deletePlaylist.setOnClickListener {
            showDialogForDeletePlaylist()
            bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.editInfo.setOnClickListener {
          //navigate to edit
        }
    }


    private fun showPlaylist(playlist: PlaylistDetailsState.Content) {
        binding.playlistName.text = playlist.playlistName
        if (playlist.playlistDetails?.isNotEmpty() == true) {
            binding.playlistDetails.visibility = View.VISIBLE
            binding.playlistDetails.text = playlist.playlistDetails
        } else {
            binding.playlistDetails.visibility = View.GONE
        }
        Glide.with(requireContext())
            .load(playlist.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.playlist_placeholder)
            .into(binding.playlistCover)
        if (!playlist.listTracks.isNullOrEmpty()) {
            trackAdapter.submitList(playlist.listTracks as ArrayList<Track>)
        }
        binding.playlistDuration.text = playlist.playlistDuration
        binding.tracksCount.text = playlist.playlistCountTrack
        if (trackAdapter.currentList.isEmpty()) {
            binding.placeholderMessage.visibility = View.VISIBLE
        } else {
            binding.placeholderMessage.visibility = View.GONE
        }
        Glide.with(requireContext())
            .load(playlist.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_playlist_placeholder)
            .into(binding.playlistCover)
        binding.playlistCover2.clipToOutline = true
        binding.playlistName2.text = playlist.playlistName
        binding.trackCount2.text = playlist.playlistCountTrack
    }

    private fun showDialogForDeleteTrack(track: Track,position: Int) {
        MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog)
            .setTitle(R.string.delete_track)
            .setMessage(R.string.delete_track2)
            .setNeutralButton(R.string.no) { _, _ ->
            }
            .setNegativeButton(R.string.yes) { _, _ ->
                    viewModel.deleteTrackFromPlaylist(track)
                updateItem(position)
            }.show()
    }

    private fun updateItem(position: Int) {
        //Костыль
        val list = trackAdapter.currentList.map { it }
        val result = list.toMutableList()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.playlist.collectLatest {
                it.tracks.remove(trackAdapter.currentList[position].trackId)
                viewModel.getData()
            }
        }
        result.removeAt(position)
        trackAdapter.submitList(result)
    }

    private fun showDialogForDeletePlaylist() {
        MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog)
            .setTitle(R.string.deletePlaylist)
            .setMessage(R.string.deletePlaylist2)
            .setNeutralButton(R.string.no) { _, _ ->
            }
            .setNegativeButton(R.string.yes) { _, _ ->
                viewModel.deletePlaylist()
            }.show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.updatePlaylist()
    }

    private fun sharePlaylist() {
        if (trackAdapter.currentList.isEmpty()) {
            Snackbar.make(requireView(),resources.getString(R.string.havent_created_any_track),Snackbar.LENGTH_LONG).show()
        } else {
            viewModel.sharePlaylist()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}