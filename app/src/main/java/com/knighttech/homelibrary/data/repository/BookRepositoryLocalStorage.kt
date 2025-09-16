package com.knighttech.homelibrary.data.repository

import com.knighttech.homelibrary.data.model.Book
import com.knighttech.homelibrary.data.repository.IBookRepository

class BookRepositoryLocalStorage(private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource) : IBookRepository {
    override suspend fun getBookByIsbn(isbn: String): Book? {
        return remoteDataSource.getBookByIsbn(isbn)
    }

    override suspend fun saveBook(book: Book) {
        localDataSource.saveBook(book)
    }

    override suspend fun getAllBooks(): List<Book> {
        return localDataSource.getAllBooks()
    }
}