package com.knighttech.homelibrary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/*@Entity(tableName = "books")
data class Book(
    @PrimaryKey var isbn_13: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "author1") var author1: String,
    @ColumnInfo(name = "author2") var author2: String,
    @ColumnInfo(name = "publisher") var publisher: String,
    @ColumnInfo(name = "published_date") var published_date: String,
    @ColumnInfo("thumbnail") var thumbnail: String
)
*/


@Serializable
data class Book(
    var isbn_13: String,
    var title: String,
    //var authors : List<String>,
    var author1: String,
    var author2: String,
    var publisher: String,
    var published_date: String,
    var thumbnail: String
)