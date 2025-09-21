package com.knighttech.homelibrary.domain.usecases

import android.util.Log
import com.knighttech.homelibrary.BuildConfig
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


private const val BASE_URL = "https://www.googleapis.com/books/v1/volumnes"
private val client = OkHttpClient()


class GetBookByIsbnUsecase {
    fun getBookByIsbn(isbn: String) {

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("www.googleapis.com")
            .addPathSegment("books")
            .addPathSegment("v1")
            .addPathSegment("volumes")
            .addQueryParameter("q", "isbn:$isbn")
            .build().toString()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${BuildConfig.BOOKS_APIKEY}")
            .get()
            .build()

        client.newCall(request).enqueue( object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                }
                Log.e("RESPONSEEEEE ", response.toString())
            }
        })
    }
}