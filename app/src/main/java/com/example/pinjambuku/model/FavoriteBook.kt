package com.example.pinjambuku.model

data class FavoriteBook(
    val idBuku: Int,
    val judul: String,
    val penulis: String,
    val penerbit: String,
    val tahun: String,
    val isbn: String,
    val kategori: String,
    val pemilik: String,
    val image: Int,
    val deskripsi: String
)
