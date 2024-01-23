package com.zaus_app.playlistmaker.view.fragments.search_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.playlistmaker.data.Track
import com.zaus_app.playlistmaker.data.base.ResultResponse
import com.zaus_app.playlistmaker.databinding.FragmentSearchBinding
import com.zaus_app.playlistmaker.view.MainActivity
import com.zaus_app.playlistmaker.view.rv_adapter.TrackAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private val trackAdapter = TrackAdapter { track ->
        viewModel.saveTrack(track)
        (requireActivity() as MainActivity).launchPlayerFragment(track)
    }
    private val historyAdapter = TrackAdapter { track ->
        (requireActivity() as MainActivity).launchPlayerFragment(track)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        setUpAdapter()
        setUpHistory()
        initSearchView()
        enableRefresh()
    }

    private fun setUpHistory() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                searchHistory.trackRecycler.apply {
                    adapter = historyAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.historyList.collectLatest { history ->
                        historyAdapter.submitList(history)
                    }
                }
            }
            clearHistory.setOnClickListener {
                viewModel.clearHistory()
                historyAdapter.submitList(null)
                historyContainer.visibility = View.GONE
            }
            updateHistoryVisibility(historyAdapter.currentList)
        }
    }

    private fun updateHistoryVisibility(history: List<Track>) {
        binding.historyContainer.isVisible = history.isNotEmpty() && binding.searchView.query.isNullOrBlank() && trackAdapter.currentList.isEmpty()
    }

    private fun initSearchView() {
        with(binding.searchView) {
            findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
                .setOnClickListener {
                    setQuery("", false)
                    clearFocus()
                    trackAdapter.submitList(null)
                    updateHistoryVisibility(historyAdapter.currentList)
                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
                }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank())
                        search(query)
                    binding.historyContainer.visibility = View.GONE
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    updateHistoryVisibility(historyAdapter.currentList)
                    return true
                }
            })
        }
    }

    private fun enableRefresh() {
        binding.refresh.setOnClickListener {
            search(binding.searchView.query.toString())
        }
    }

    private fun search(term: String) {
        lifecycleScope.launch {
            viewModel.search(term).collectLatest { result ->
                when (result) {
                    is ResultResponse.Loading -> {
                        binding.nfPlaceholder.root.visibility = View.GONE
                        binding.ncPlaceholder.root.visibility = View.GONE
                        binding.refresh.visibility = View.GONE
                        trackAdapter.submitList(null)
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResultResponse.Success -> {
                        val tracks = result.data.results
                        if (tracks.isNotEmpty()) {
                            binding.progressBar.visibility = View.GONE
                            trackAdapter.submitList(tracks)
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.nfPlaceholder.root.visibility = View.VISIBLE
                        }
                    }

                    is ResultResponse.Error -> {
                        val errorMessage = result.message
                        binding.progressBar.visibility = View.GONE
                        binding.ncPlaceholder.root.visibility = View.VISIBLE
                        binding.refresh.visibility = View.VISIBLE

                    }
                }
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

}