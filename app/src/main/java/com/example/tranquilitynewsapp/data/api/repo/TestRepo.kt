package com.example.tranquilitynewsapp.data.api.repo

import com.example.tranquilitynewsapp.data.api.NewsService
import javax.inject.Inject

class TestRepo @Inject constructor(private val newsService: NewsService) {

    suspend fun getAll() = newsService.getHeadlines()
}