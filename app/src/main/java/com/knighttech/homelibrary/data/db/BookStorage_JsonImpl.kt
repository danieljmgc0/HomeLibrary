package com.knighttech.homelibrary.data.db

import android.content.Context
import android.util.Log
import android.util.MutableInt
import com.knighttech.homelibrary.domain.model.Book
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class BookStorage_JsonImpl: IBookStorage {

    override fun getAllBooks(context: Context): List<Book> {
        val file = File(context.filesDir, "books.json")
        if (!file.exists() || file.length() == 0L) return emptyList()

        //return Json.decodeFromString<List<Book>>(jsonString)

        val jsonString = file.readText()
        return Json.decodeFromString(jsonString)
    }

    override fun getBookByIsbn(context: Context, isbn: String): Book {
        /*val file = File(context.filesDir, "books.json")
        val allBooks: MutableList<Book> = if (file.exists() && file.length() > 0) {
            val json = file.readText()
            Json.decodeFromString<List<Book>>(json).toMutableList()
            } else {
                mutableListOf()
            }
        */
        var allBooks = getAllBooks(context)
        var book: Book? = null
        try{
            book = allBooks.first {it.isbn_13 == isbn }
            return book
        } catch (e: NoSuchElementException){
            throw e
        }
    }

    override fun deleteBook(context: Context, isbn: String) {
        val file = File(context.filesDir, "books.json")
        if(file.exists()){
            val books = getAllBooks(context).toMutableList()
            books.removeIf { it.isbn_13 == isbn }
            file.writeText(Json.encodeToString(books))
        }
    }

    override fun saveBook(context: Context, book: Book) {
        val file = File(context.filesDir, "books.json")
        try{
            var book = getBookByIsbn(context, book.isbn_13)
            throw IllegalStateException("El libro ya existe en la base de datos")
        } catch (e: NoSuchElementException){
            val books: MutableList<Book> = if (file.exists() && file.length() > 0) {
                val json = file.readText()
                Json.decodeFromString<List<Book>>(json).toMutableList()
            } else {
                mutableListOf()
            }
            books.add(book)
            file.writeText(Json.encodeToString(books))
        }
    }
}