package com.brhn.xpnsr.activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brhn.xpnsr.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCallActivity : BaseActivity() {
    @Composable
    override fun ScreenContent(modifier: Modifier) {
        ApiCallScreen()
    }

    override fun getAppBarTitle(): String {
        return "API Calls"
    }
}

@Composable
fun ApiCallScreen() {
    var posts by remember { mutableStateOf(listOf<String>()) }
    var isLoading by remember { mutableStateOf(false) }

    // Setup Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    Column(modifier = Modifier.padding(8.dp)) {
        Spacer(modifier = Modifier.height(64.dp))
        Button(onClick = {
            isLoading = true

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.getPosts().execute()
                    if (response.isSuccessful && response.body() != null) {
                        val fetchedPosts = response.body()!!
                        if (fetchedPosts.isNotEmpty()) {
                            posts = fetchedPosts.map { it.title }
                        } else {
                            posts = listOf("No posts found!")
                        }
                    } else {
                        posts = listOf("Failed to fetch data!")
                    }
                } catch (e: Exception) {
                    posts = listOf("Error: ${e.message}")
                }
                isLoading = false
            }
        }) {
            Text("Call API")
        }

        if (isLoading) {
            Text("Loading...")
        } else {
            LazyColumn {
                items(posts) { post ->
                    Text(
                        text = post,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}