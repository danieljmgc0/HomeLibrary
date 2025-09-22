package com.knighttech.homelibrary.data.repository

import android.util.Log
import com.knighttech.homelibrary.domain.model.Book


val b : Book = Book("1234", "Titulo", "autor", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN");
val b2 : Book = Book("5678", "Titulo2", "autor2", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN");

class BookRepositoryLocalStorage() : IBookRepository {
    override fun getBookByIsbn(isbn: String): Book? {
        //return remoteDataSource.getBookByIsbn(isbn)
        Log.d("MYLOG", b.isbn_13)
        return b;
    }

    override fun saveBook(book: Book) {
        //localDataSource.saveBook(book)
        Log.d("MYLOG", b.isbn_13)
    }

    override fun getAllBooks(): List<Book> {
        //return localDataSource.getAllBooks()
        val books = listOf(b, b2)
        Log.d("MYLOG", b.isbn_13)
        Log.d("MYLOG", b2.isbn_13)
        return books
    }
}