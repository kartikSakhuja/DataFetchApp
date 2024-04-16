package com.example.datafetchapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafetchapp.api.ApiService
import com.example.datafetchapp.api.AppModule
import com.example.datafetchapp.data.DataFetch
import com.example.datafetchapp.domain.DataFetchRepository
import kotlinx.coroutines.launch

class DataFetchViewModel : ViewModel() {

    private val apiService: ApiService = AppModule.providesdata()

    private val postRepository = DataFetchRepository(apiService)

    private val _dataFetchs = MutableLiveData<List<DataFetch>>()
    val dataFetchs: LiveData<List<DataFetch>> get() = _dataFetchs

    fun fetchPosts() {
        viewModelScope.launch {
            _dataFetchs.value = postRepository.fetchPosts()
        }
    }
}