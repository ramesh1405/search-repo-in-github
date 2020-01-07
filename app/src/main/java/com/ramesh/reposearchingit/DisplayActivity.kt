package com.ramesh.reposearchingit

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager

import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import com.google.android.material.navigation.NavigationView
import com.ramesh.reposearchingit.adapters.DisplayAdapter
import com.ramesh.reposearchingit.app.Constants
import com.ramesh.reposearchingit.extensions.showErrorMessage
import com.ramesh.reposearchingit.extensions.toast
import com.ramesh.reposearchingit.models.Repository
import com.ramesh.reposearchingit.models.SearchResponse
import com.ramesh.reposearchingit.retrofit.GithubAPIService
import com.ramesh.reposearchingit.retrofit.RetroClient

import java.util.HashMap

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_display.*

class DisplayActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var browsedRepositories: List<Repository> = mutableListOf()
    private lateinit var displayAdapter: DisplayAdapter
    private val githubAPIService: GithubAPIService by lazy {
        RetroClient.githubAPIService
    }

    companion object {

        private val TAG = DisplayActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        setSupportActionBar(distoolbar)
        supportActionBar!!.title = "Showing Browsed Results"

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager

        val intent = intent
        if (intent.getIntExtra(Constants.KEY_QUERY_TYPE, -1) == Constants.SEARCH_BY_REPO) {
            val queryRepo = intent.getStringExtra(Constants.KEY_REPO_SEARCH)
            val repoLanguage = intent.getStringExtra(Constants.KEY_LANGUAGE)
            fetchRepositories(queryRepo, repoLanguage)
        }
    }

    private fun fetchRepositories(queryRepo: String, repoLanguage: String) {
        var queryRepo1 = queryRepo
        val query = HashMap<String, String>()

        if (repoLanguage.isNotEmpty())
            queryRepo1 += " language:$repoLanguage"
        Log.i(TAG, "+++++++querry:$queryRepo1")
        query["q"] = queryRepo1
        query["sort"] = "stars"
        query["order"] = "desc"

        githubAPIService.searchRepositories(query).enqueue(object : Callback<SearchResponse> {

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    Log.i(TAG, "posts loaded from API $response")
                    response.body()?.items?.let {
                        browsedRepositories = it
                    }

                    if (browsedRepositories.isNotEmpty())
                        setupRecyclerView(browsedRepositories)
                    else
                        toast("No Items Found")
                } else {
                    Log.i(TAG, "error $response")
                    showErrorMessage(response.errorBody()!!)
                   // Util.showErrorMessage(this@DisplayActivity, response.errorBody()!!)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                toast(t.message ?: "Error fetching results")
            }
        })
    }

    private fun setupRecyclerView(items: List<Repository>) {
        displayAdapter = DisplayAdapter(this, items)
        recyclerView.adapter = displayAdapter
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        when (menuItem.itemId) {

            R.id.item_browsed_results -> {
                showBrowsedResults()
                supportActionBar!!.title = "Showing Browsed Results"
            }
        }
        return true
    }

    private fun showBrowsedResults() {
        displayAdapter.swap(browsedRepositories)
    }
}
