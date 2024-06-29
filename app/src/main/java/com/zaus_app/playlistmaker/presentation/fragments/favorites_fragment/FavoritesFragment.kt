package com.zaus_app.playlistmaker.presentation.fragments.favorites_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.playlistmaker.databinding.FragmentFavoritesBinding
import com.zaus_app.playlistmaker.presentation.MainActivity
import com.zaus_app.playlistmaker.presentation.rv_adapter.TrackAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoritesViewModel by viewModel()
    private val trackAdapter = TrackAdapter { track ->
        (requireActivity() as MainActivity).launchPlayerFragment(track)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { stateLiveData ->
            when (stateLiveData) {
                is FavoritesState.Ready-> {
                    binding.placeholder.root.visibility = View.GONE
                    trackAdapter.submitList(stateLiveData.favoritesList)
                }
                FavoritesState.Error -> {
                    trackAdapter.submitList(null)
                    binding.placeholder.root.visibility = View.VISIBLE
                }
                FavoritesState.Loading -> {}
                else -> {}
            }
        }
    }


    private fun setUpAdapter() {
        binding.trackRecycler.apply {
            adapter = trackAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}