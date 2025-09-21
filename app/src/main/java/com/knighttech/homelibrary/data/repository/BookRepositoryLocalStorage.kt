package com.knighttech.homelibrary.data.repository

import android.util.Log
import com.knighttech.homelibrary.domain.model.Book


val b : Book = Book("1234", "Titulo", "autor");
val b2 : Book = Book("5678", "Titulo2", "autor2");

class BookRepositoryLocalStorage() : IBookRepository {
    override fun getBookByIsbn(isbn: String): Book? {
        //return remoteDataSource.getBookByIsbn(isbn)
        Log.d("MYLOG", b.isbn)
        return b;
    }

    override fun saveBook(book: Book) {
        //localDataSource.saveBook(book)
        Log.d("MYLOG", b.isbn)
    }

    override fun getAllBooks(): List<Book> {
        //return localDataSource.getAllBooks()
        val books = listOf(b, b2)
        Log.d("MYLOG", b.isbn)
        Log.d("MYLOG", b2.isbn)
        return books
    }
}