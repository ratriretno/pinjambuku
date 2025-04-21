package com.example.pinjambuku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao

interface ExampleBorrowedBookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exampleBorrowedBookEntity: ExampleBorrowedBookEntity)

    @Update
    suspend fun update(exampleBorrowedBookEntity: ExampleBorrowedBookEntity)

    @Delete
    suspend fun delete(exampleBorrowedBookEntity: ExampleBorrowedBookEntity)

    @Query("SELECT * from borrowed_book")
    fun getAllBorrowedBook(): LiveData<List<ExampleBorrowedBookEntity>>
    //fun getAllFavoriteMovie(): LiveData<List<Movie>>


    @Query("SELECT COUNT(*) FROM borrowed_book WHERE idBuku = :idBook")
    suspend fun isBorrowed(idBook: String): Int
}