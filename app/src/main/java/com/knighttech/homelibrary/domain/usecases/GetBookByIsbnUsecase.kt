package com.knighttech.homelibrary.domain.usecases

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.knighttech.homelibrary.BuildConfig
import com.knighttech.homelibrary.domain.model.Book
import com.knighttech.homelibrary.domain.model.BookSearchResponse
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
    suspend fun getBookByIsbn(isbn: String, onResult: (Book?) -> Unit) {

        val url = HttpUrl.Builder()
            .scheme("https")
            .host("www.googleapis.com")
            .addPathSegment("books")
            .addPathSegment("v1")
            .addPathSegment("volumes")
            .addQueryParameter("q", "isbn:$isbn")
            .addQueryParameter("key", BuildConfig.BOOKS_APIKEY)
            .build().toString()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue( object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                onResult(null)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val bodyStr = response.body?.string()
                    val gson = Gson()
                    val bookResponse = gson.fromJson(bodyStr, BookSearchResponse::class.java)
                    val book = mapResponseToBook(bookResponse)
                    onResult(book)
                }
            }
        })
    }

    fun mapResponseToBook(response: BookSearchResponse): Book? {
        val volume = response.items?.firstOrNull()?.volumeInfo ?: return null

        val isbn13 = volume.industryIdentifiers
            ?.firstOrNull { it.type == "ISBN_13" }?.identifier ?: return null

        return Book(
            isbn_13 = isbn13,
            title = volume.title ?: "Título desconocido",
            author1 = volume.authors?.firstOrNull() ?: "Autor desconocido",
            author2 = volume.authors?.firstOrNull() ?: "NOAUTOR2",
            publisher = volume.publisher ?: "Editorial desconocida",
            published_date = volume.publishedDate ?: "Fecha desconocida",
            thumbnail = volume.imageLinks?.thumbnail ?: ""
        )
    }
}