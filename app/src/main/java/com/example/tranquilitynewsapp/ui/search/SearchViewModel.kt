package com.example.tranquilitynewsapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tranquilitynewsapp.data.api.repo.NewsRepository
import com.example.tranquilitynewsapp.models.NewsResponse
import com.example.tranquilitynewsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val searchNewsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val searchPageNum = 1

    init {
        getSearchNews("")
    }

    fun getSearchNews(query: String) = viewModelScope.launch {
        searchNewsLiveData.postValue(Resource.Loading())
        val response = newsRepository.getSearchNews(query, pageNumber = searchPageNum)
        if (response.isSuccessful) {
            response.body().let { res ->
                searchNewsLiveData.postValue(Resource.Success(res))
            }
        } else {
            searchNewsLiveData.postValue(Resource.Error(message = response.message()))
        }
    }
}