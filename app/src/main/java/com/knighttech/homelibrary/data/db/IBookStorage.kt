package com.knighttech.homelibrary.data.db

import android.content.Context
import com.knighttech.homelibrary.domain.model.Book

interface IBookStorage {

    fun getAllBooks(context: Context): List<Book>
    @Throws(IllegalStateException::class)
    fun saveBook(context: Context, book: Book)
    fun deleteBook(context: Context, isbn: String)
    @Throws(NoSuchElementException::class)
    fun getBookByIsbn(context: Context, isbn: String) : Book

}