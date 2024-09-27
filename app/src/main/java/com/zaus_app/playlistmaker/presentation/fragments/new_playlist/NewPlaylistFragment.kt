package com.zaus_app.playlistmaker.presentation.fragments.new_playlist

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.zaus_app.playlistmaker.R
import com.zaus_app.playlistmaker.databinding.FragmentMediaBinding
import com.zaus_app.playlistmaker.databinding.FragmentNewPlaylistBinding
import com.zaus_app.playlistmaker.presentation.fragments.playlist_fragment.PlaylistsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPlaylistFragment : Fragment() {
    private var _binding: FragmentNewPlaylistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewPlaylistViewModel by viewModel()
    private var playlistName: String = ""
    private var playlistDescription: String = ""
    private var playlistCoverUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        restoreInstanceState(savedInstanceState)
    }

    private fun initUI() {
        binding.inputName.doOnTextChanged { text, _, _, _ ->
            playlistName = text?.toString().orEmpty()
            binding.createPlaylist.isEnabled = playlistName.isNotBlank()
        }

        binding.inputDescription.doOnTextChanged { text, _, _, _ ->
            playlistDescription = text?.toString().orEmpty()
        }

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                playlistCoverUri = uri
                binding.addPicture.apply {
                    setImageURI(uri)
                    clipToOutline = true
                }
            }
        }

        binding.addPicture.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            if (state is NewPlaylistState.Success) {
                viewLifecycleOwner.lifecycleScope.launch {
                    Toast.makeText(requireContext(),"Плейлист $playlistName создан",Toast.LENGTH_LONG).show()
                    delay(2000)
                    findNavController().navigateUp()
                }


                //binding.loadingIndicator.visibility = View.GONE
            }
        }

        binding.createPlaylist.setOnClickListener {
            binding.loadingIndicator.visibility = View.VISIBLE
            viewModel.addPlaylist(playlistName, playlistDescription, playlistCoverUri)
            parentFragmentManager.popBackStack()
        }

        binding.btnBackFromNewPlayList.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        playlistCoverUri?.let {
            outState.putString("playlistCover", it.toString())
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.getString("playlistCover")?.toUri()?.let {
            playlistCoverUri = it
            binding.addPicture.apply {
                setImageURI(it)
                clipToOutline = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}