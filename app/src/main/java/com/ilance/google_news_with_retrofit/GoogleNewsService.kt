package com.ilance.google_news_with_retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleNewsService {

    @GET("/rss")
    fun getNews(
      @Query("hl") hl : String,
      @Query("gl") gl : String,
      @Query("ceid") ceid : String
    ): Call<RespNewsRss>
}