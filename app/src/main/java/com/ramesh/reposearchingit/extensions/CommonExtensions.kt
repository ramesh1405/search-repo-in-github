package com.ramesh.reposearchingit.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast

import com.google.gson.GsonBuilder

import com.ramesh.reposearchingit.models.ErrorResponse

import java.io.IOException

import okhttp3.ResponseBody

//extension function
fun Context.showErrorMessage(errorBody: ResponseBody){
    val gson = GsonBuilder().create()
    try {
        val errorResponse = gson.fromJson(errorBody.string(), ErrorResponse::class.java)
        toast(errorResponse.message!!)
    } catch (e: IOException) {
        Log.i("Exception ", e.toString())
    }
}
//extension function
fun Context.toast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}


