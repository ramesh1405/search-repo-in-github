package com.ramesh.reposearchingit.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroClient {
    private val BASE_URL = "https://api.github.com/"
    private val retrofit: Retrofit= Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    val githubAPIService: GithubAPIService= retrofit.create(GithubAPIService::class.java)
}
//https://api.github.com/search/repositories?q=JSONparser+language:java