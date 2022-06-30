package com.example.tranquilitynewsapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tranquilitynewsapp.data.api.repo.NewsRepository
import com.example.tranquilitynewsapp.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
    ): ViewModel() {

    init {
        getSavedArticle()
    }

    fun getSavedArticle() = viewModelScope.launch(Dispatchers.IO) {
        val res = newsRepository.getFavoriteNews()
        println("save to BD: " + "${res.size}")
        newsRepository.getFavoriteNews()
    }

    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        newsRepository.addToFavorite(article = article)
    }
}