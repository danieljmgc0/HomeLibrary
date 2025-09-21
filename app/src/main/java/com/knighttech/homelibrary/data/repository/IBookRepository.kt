package com.knighttech.homelibrary.data.repository

import com.knighttech.homelibrary.domain.model.Book

interface IBookRepository {
    fun getBookByIsbn(isbn: String): Book?
    fun saveBook(book: Book)
    fun getAllBooks(): List<Book>
}