package com.knighttech.homelibrary.data.repository

import com.knighttech.homelibrary.domain.model.Book

interface IBookRepository {
    suspend fun getBookByIsbn(isbn: String): Book?
    suspend fun saveBook(book: Book)
    suspend fun getAllBooks(): List<Book>
}