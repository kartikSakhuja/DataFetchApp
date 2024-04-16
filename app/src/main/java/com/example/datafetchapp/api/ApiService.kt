package com.example.datafetchapp.api

import com.example.datafetchapp.data.DataFetch
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<DataFetch>
}

