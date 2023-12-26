package com.zaus_app.playlistmaker.view.fragments.search_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zaus_app.playlistmaker.data.Track
import com.zaus_app.playlistmaker.data.base.ResultResponse
import com.zaus_app.playlistmaker.databinding.FragmentSearchBinding
import com.zaus_app.playlistmaker.view.rv_adapter.TrackAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val trackAdapter = TrackAdapter(object : TrackAdapter.OnItemClickListener {
        override fun click(track: Track) {
            viewModel.saveTrack(track)
        }
    })
    private val historyAdapter = TrackAdapter(object : TrackAdapter.OnItemClickListener {
        override fun click(track: Track) {
        }
    })
    private val viewModel: SearchViewModel by viewModels()
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
/*        with(binding.searchHistory) {
            trackRecycler.apply {
                adapter = historyAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            var history = emptyList<Track>()
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    history = viewModel.getHistory()
                }
            }
            if (history.isNotEmpty()) {
                historyAdapter.submitList(history)
            }
        }*/
    }

    private fun initSearchView() {
        with(binding.searchView) {
            findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
                .setOnClickListener {
                    setQuery("", false)
                    clearFocus()
                    trackAdapter.submitList(null)
                    val inputMethodManager =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
                }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank())
                        search(query)
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
      /*              if (query.isNullOrBlank())
                        binding.searchHistory.root.visibility = View.VISIBLE
                    else
                        binding.searchHistory.root.visibility = View.GONE*/
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
                        binding.searchHistory.root.visibility = View.GONE
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