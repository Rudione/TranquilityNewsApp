package com.example.tranquilitynewsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tranquilitynewsapp.models.Article


interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Delete
    fun delete(article: Article)
}