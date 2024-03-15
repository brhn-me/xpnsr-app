package com.brhn.xpnsr.services

import retrofit2.Call
import retrofit2.http.GET


data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)


interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}
