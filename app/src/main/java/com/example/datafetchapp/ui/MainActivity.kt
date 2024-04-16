package com.example.datafetchapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.datafetchapp.data.DataFetch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    private val dataViewModel: DataFetchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dataFetchs by dataViewModel.dataFetchs.observeAsState(initial = emptyList())
            PostList(dataFetchs = dataFetchs)
        }
    }

    override fun onStart() {
        super.onStart()
        dataViewModel.fetchPosts()
    }

    @Composable
    fun PostList(dataFetchs: List<DataFetch>) {
        LazyColumn {
            items(dataFetchs) { dataFetch ->
                PostItem(dataFetch)
                DetailView(dataFetch)
            }
        }
    }

    @Composable
    fun PostItem(dataFetch: DataFetch) {
        Text(text = "UserId : " + dataFetch.userId.toString())
        Text(text = "Id : " + dataFetch.id.toString())
        Text(text = "Title : " + dataFetch.title)
        Text(text = "Body : " + dataFetch.body)
    }

    @Composable
    fun DetailView(dataFetch: DataFetch) {
        var additionalDetails by remember { mutableStateOf("") }
        var computationTime by remember { mutableStateOf(0L) }

        Column {
            Text(text = "Title: ${dataFetch.title}")
            Text(text = "Description: ${dataFetch.id}")

            Button(onClick = {
                val startTime = System.currentTimeMillis()
                lifecycleScope.launch {
                    computeAdditionalDetails(dataFetch) { result ->
                        additionalDetails = result
                        computationTime = System.currentTimeMillis() - startTime
                    }
                }
            }) {
                Text(text = "Compute Additional Details")
            }
            Text(text = "Computation Time: $computationTime ms")
        }
    }

    private suspend fun computeAdditionalDetails(dataFetch: DataFetch, callback: (String) -> Unit) {
        withContext(Dispatchers.Default) {
            val startTime = System.currentTimeMillis()

            val result = "Additional details for ${dataFetch.id}" // Compute additional details here
            callback(result)

            val endTime = System.currentTimeMillis()
            val totalTime = endTime - startTime
            Log.d("Heavy computation took", "$totalTime ms")
        }
    }

}