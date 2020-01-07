package com.ramesh.reposearchingit

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ramesh.reposearchingit.app.Constants
import com.ramesh.reposearchingit.extensions.isNotEmpty
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    companion object{
        private val TAG: String=MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }
    // Search repositories on github
    fun listRepositories(view: View) {
        Log.i(TAG,"+++++start of listRepo method++++++")
        if (etRepoName.isNotEmpty(inputLayoutRepoName)) {
            val queryRepo = etRepoName.text.toString()
            val repoLanguage = etLanguage.text.toString()
            val intent = Intent(this@MainActivity, DisplayActivity::class.java)

            intent.putExtra(Constants.KEY_QUERY_TYPE, Constants.SEARCH_BY_REPO)
            intent.putExtra(Constants.KEY_REPO_SEARCH, queryRepo)
            intent.putExtra(Constants.KEY_LANGUAGE, repoLanguage)
            startActivity(intent)
        }
    }
}