package com.ramesh.reposearchingit.models

import com.google.gson.annotations.SerializedName

class SearchResponse {
    @SerializedName("total_count")
    var totalCount: Int = 0
    var items: List<Repository>? = null
}
