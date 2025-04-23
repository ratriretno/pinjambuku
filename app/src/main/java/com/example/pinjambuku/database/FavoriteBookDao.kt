package com.example.pinjambuku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao

interface FavoriteBookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteBookEntity: FavoriteBookEntity)

    @Update
    suspend fun update(favoriteBookEntity: FavoriteBookEntity)

    @Delete
    suspend fun delete(favoriteBookEntity: FavoriteBookEntity)

    @Query("SELECT * from favorite_book")
    fun getAllFavoriteBook(): LiveData<List<FavoriteBookEntity>>
    //fun getAllFavoriteMovie(): LiveData<List<Movie>>


    @Query("SELECT COUNT(*) FROM favorite_book WHERE idBuku = :idBook")
    suspend fun isFavorite(idBook: String): Int

}