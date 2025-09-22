package com.knighttech.homelibrary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey var isbn_13: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "author1") var author1: String,
    @ColumnInfo(name = "author2") var author2: String,
    @ColumnInfo(name = "publisher") var publisher: String,
    @ColumnInfo(name = "published_date") var published_date: String,
    @ColumnInfo("thumbnail") var thumbnail: String
)



// Puedes crear datos de ejemplo
object SampleData {
    val books = listOf(
        Book("9780140449136", "Odisea", "Homero", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788420413380", "Don Quijote de la Mancha", "Miguel de Cervantes", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN"),
        Book("9788467037635", "Cien años de soledad", "Gabriel García Márquez", "NOAUTOR", "MIEDIT", "HOY", "SINIMAGEN")

    )
}