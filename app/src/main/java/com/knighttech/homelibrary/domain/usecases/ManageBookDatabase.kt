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

    fun saveBook(context: Context, book: Book) {
        try{
            val storage: IBookStorage = BookStorage_JsonImpl()
            storage.saveBook(context, book)
        } catch (e: IllegalStateException){
            throw e
        }
    }

    fun deleteBook(context: Context, isbn: String) {
        val storage: IBookStorage = BookStorage_JsonImpl()
        storage.deleteBook(context, isbn)
    }

    fun getBookByIsbn(context: Context, isbn: String): Book? {
        try{
            val storage: IBookStorage = BookStorage_JsonImpl()
            return storage.getBookByIsbn(context, isbn)
        } catch (e: NoSuchElementException){
            return null
        }
    }

    fun getAllBooks(context: Context): List<Book> {
        val storage: IBookStorage = BookStorage_JsonImpl()

        var books = storage.getAllBooks(context)
        return books
    }

}