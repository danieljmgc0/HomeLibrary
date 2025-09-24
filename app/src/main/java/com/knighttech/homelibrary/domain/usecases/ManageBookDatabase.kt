package com.knighttech.homelibrary.domain.usecases


import android.content.Context
import android.util.Log
//import com.knighttech.homelibrary.data.db.BookDatabase
import com.knighttech.homelibrary.data.db.BookStorage_JsonImpl
import com.knighttech.homelibrary.data.db.IBookStorage
import com.knighttech.homelibrary.domain.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ManageBookDatabase {

    fun saveBook(book: Book, context: Context) {
        Log.d("BOOOK ES ", book.toString())
        val storage: IBookStorage = BookStorage_JsonImpl()
        storage.saveBook(context, book)
    }

    fun deleteBook(isbn: String, context: Context) {
        val storage: IBookStorage = BookStorage_JsonImpl()
        storage.deleteBook(context, isbn)
    }

    fun getBookByIsbn(isbn: String, context: Context): Book? {
        val storage: IBookStorage = BookStorage_JsonImpl()
        return storage.getBookByIsbn(context, isbn)
    }

    fun getAllBooks(context: Context): List<Book> {
        val storage: IBookStorage = BookStorage_JsonImpl()
        var books = storage.getAllBooks(context)
        Log.d("BOOOKS ES", books.toString())
        return books
    }

}