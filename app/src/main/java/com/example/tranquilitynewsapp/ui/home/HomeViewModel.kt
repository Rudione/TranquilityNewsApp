package com.example.tranquilitynewsapp.ui.home

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
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
    ): ViewModel() {

    val newsLiveData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private val newsPage = 1

    init {
        getNews("ua")
    }

    private fun getNews(countryCode: String) =
        viewModelScope.launch {
            newsLiveData.postValue(Resource.Loading())
            val response = newsRepository.getNews(countryCode = countryCode, pageNumber = newsPage)
            if (response!!.isSuccessful) {
                response.body().let { res ->
                    newsLiveData.postValue(Resource.Success(res))
                }
            } else {
                newsLiveData.postValue(Resource.Error(message = response.message()))
            }
        }

}