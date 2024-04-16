package com.example.datafetchapp.domain

import com.example.datafetchapp.api.ApiService
import com.example.datafetchapp.data.DataFetch

class DataFetchRepository(private val apiService: ApiService) {
    suspend fun fetchPosts(): List<DataFetch> {
        return apiService.getPosts()
    }
}