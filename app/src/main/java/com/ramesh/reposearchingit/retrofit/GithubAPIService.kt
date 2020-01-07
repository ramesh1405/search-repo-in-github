package com.ramesh.reposearchingit.retrofit

import com.ramesh.reposearchingit.models.SearchResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GithubAPIService {
    @GET("search/repositories")
    fun searchRepositories(@QueryMap options: Map<String, String>): Call<SearchResponse>
}


