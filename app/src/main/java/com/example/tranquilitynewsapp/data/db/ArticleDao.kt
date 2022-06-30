package com.example.tranquilitynewsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tranquilitynewsapp.models.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}