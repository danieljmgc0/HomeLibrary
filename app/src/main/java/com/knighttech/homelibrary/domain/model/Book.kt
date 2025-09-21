package com.knighttech.homelibrary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey var isbn: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "author") var author: String,
)


// Puedes crear datos de ejemplo
object SampleData {
    val books = listOf(
        Book("9780140449136", "Odisea", "Homero"),
        Book("9788420413380", "Don Quijote de la Mancha", "Miguel de Cervantes"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez")

    )
}