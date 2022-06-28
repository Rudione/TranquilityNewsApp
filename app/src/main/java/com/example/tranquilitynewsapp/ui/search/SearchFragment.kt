package com.example.tranquilitynewsapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tranquilitynewsapp.R
import com.example.tranquilitynewsapp.databinding.FragmentDetailsBinding
import com.example.tranquilitynewsapp.databinding.FragmentSearchBinding
import com.example.tranquilitynewsapp.ui.adapters.NewsAdapter
import com.example.tranquilitynewsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeNewsAdapter()
        runSearch()

    }

    private fun runSearch() {
        var job: Job? = null
        ed_search.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text.let {
                    if(it.toString().isNotEmpty()) {
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }
    }

    private fun observeNewsAdapter() {
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { res ->
            when(res) {
                is Resource.Error -> {
                    pb_search.visibility = View.INVISIBLE
                    res.data?.apply {
                        Log.d("AAA", "${this@SearchFragment}")
                    }
                }

                is Resource.Loading -> {
                    pb_search.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    pb_search.visibility = View.INVISIBLE
                    res.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                else -> {
                    Log.d("AAA", "$res")
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        rv_search.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}