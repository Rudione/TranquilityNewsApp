package com.example.tranquilitynewsapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tranquilitynewsapp.R
import com.example.tranquilitynewsapp.databinding.FragmentDetailsBinding
import com.example.tranquilitynewsapp.databinding.FragmentHomeBinding
import com.example.tranquilitynewsapp.ui.adapters.NewsAdapter
import com.example.tranquilitynewsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeNewsAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view.findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
        }

    }

    private fun observeNewsAdapter() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) { res ->
            when(res) {
                is Resource.Error -> {
                    pb_fragment_home.visibility = View.INVISIBLE
                    res.data?.apply {
                        Log.d("AAA", "${this@HomeFragment}")
                    }
                }

                is Resource.Loading -> {
                    pb_fragment_home.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    pb_fragment_home.visibility = View.INVISIBLE
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
        rc_home_news.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}