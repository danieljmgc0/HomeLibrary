package com.knighttech.homelibrary.domain.usecases

import com.knighttech.homelibrary.data.repository.IBookRepository
import com.knighttech.homelibrary.domain.model.Book

class addBookUseCase(private val repository: IBookRepository) {

    fun addBook(book: Book){
        repository.saveBook(book);
    }
}