package com.ramesh.reposearchingit.models

import com.google.gson.annotations.SerializedName

class Repository {
    var id: Int = 0

    var name: String? = null

    var language: String? = null

    @SerializedName("html_url")
    var htmlUrl: String? = null

    var description: String? = null

    @SerializedName("stargazers_count")
    var stars: Int? = null

    @SerializedName("watchers_count")
    var watchers: Int? = null

    var forks: Int? = null

    var owner: Owner? = null
}
