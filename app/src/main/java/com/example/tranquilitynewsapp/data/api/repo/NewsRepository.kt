package com.example.tranquilitynewsapp.data.api.repo

import com.example.tranquilitynewsapp.data.api.NewsService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService?) {
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService?.getHeadlines(country = countryCode, page = pageNumber)
}