package com.knighttech.homelibrary.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.knighttech.homelibrary.domain.model.Book
//import com.knighttech.homelibrary.domain.model.BookDao

/*
@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null
        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}*/