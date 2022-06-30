package com.example.tranquilitynewsapp.data.api.repo

import com.example.tranquilitynewsapp.data.api.NewsService
import com.example.tranquilitynewsapp.data.db.ArticleDao
import com.example.tranquilitynewsapp.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val articleDao: ArticleDao
) {
    suspend fun getNews(countryCode: String, pageNumber: Int) =
        newsService.getHeadlines(country = countryCode, page = pageNumber)

    suspend fun getSearchNews(query: String, pageNumber: Int) =
        newsService.getEverything(query = query, page = pageNumber)

    fun getFavoriteNews() =
        articleDao.getAllArticles()

    suspend fun addToFavorite(article: Article) =
        articleDao.insert(article = article)


    suspend fun deleteFromFavorite(article: Article) =
        articleDao.delete(article = article)

}