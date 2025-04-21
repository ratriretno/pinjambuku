package com.example.pinjambuku.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "borrowed_book")

data class ExampleBorrowedBookEntity(

    @PrimaryKey
    @ColumnInfo(name = "idBuku")
    val idBuku: String,           // Unique identifier

    @ColumnInfo(name = "judul")
    val judul: String? = null,

    @ColumnInfo(name = "penulis")
    val penulis: String? = null,

    @ColumnInfo(name = "penerbit")
    val penerbit: String? = null,

    @ColumnInfo(name = "tahun")
    val tahun: String? = null,

    @ColumnInfo(name = "isbn")
    val isbn: String? = null,

    @ColumnInfo(name = "kategori")
    val kategori: String? = null,

    @ColumnInfo(name = "pemilik")
    val pemilik: String? = null,

    @ColumnInfo(name = "image")
    val image: Int? = null,

    @ColumnInfo(name = "deskripsi")
    val deskripsi:String? = null

)
