package com.example.tranquilitynewsapp.data.api

import com.example.tranquilitynewsapp.utils.Constans.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/everything")
    fun getEverything(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    )

    @GET("")
    fun getHeadlines(
        @Query("country") country: String = "ua",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    )
}