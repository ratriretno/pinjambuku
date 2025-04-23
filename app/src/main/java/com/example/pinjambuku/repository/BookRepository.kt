package com.example.pinjambuku.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.pinjambuku.database.ExampleBookRoomDatabase
import com.example.pinjambuku.database.ExampleBorrowedBookDao
import com.example.pinjambuku.database.ExampleBorrowedBookEntity
import com.example.pinjambuku.database.FavoriteBookDao
import com.example.pinjambuku.database.FavoriteBookEntity

class BookRepository (application: Application){

    private val eExampleBorrowedBookDao: ExampleBorrowedBookDao
    private val fFavoriteBookDao: FavoriteBookDao

    init {
        val db = ExampleBookRoomDatabase.getDatabase(application)
        eExampleBorrowedBookDao = db.exampleBorrowedBookDao()
        fFavoriteBookDao = db.favoriteBookDao()
    }

    suspend fun insert(exampleBorrowedBookEntity: ExampleBorrowedBookEntity) = eExampleBorrowedBookDao.insert(exampleBorrowedBookEntity)
    suspend fun delete(exampleBorrowedBookEntity: ExampleBorrowedBookEntity) = eExampleBorrowedBookDao.delete(exampleBorrowedBookEntity)
    suspend fun update(exampleBorrowedBookEntity: ExampleBorrowedBookEntity) = eExampleBorrowedBookDao.update(exampleBorrowedBookEntity)

    fun getAllBorrowedBook(): LiveData<List<ExampleBorrowedBookEntity>> = eExampleBorrowedBookDao.getAllBorrowedBook()
    //fun getAllFavoriteMovie(): LiveData<List<Movie>> = mMovieDao.getAllFavoriteMovie()

    //to convert the Int result (in Dao) into a Boolean
    suspend fun isBorrowed(idBook: String): Boolean {
        return eExampleBorrowedBookDao.isBorrowed(idBook) > 0
    }

    suspend fun insert(favoriteBookEntity: FavoriteBookEntity) = fFavoriteBookDao.insert(favoriteBookEntity)
    suspend fun delete(favoriteBookEntity: FavoriteBookEntity) = fFavoriteBookDao.delete(favoriteBookEntity)
    suspend fun update(favoriteBookEntity: FavoriteBookEntity) = fFavoriteBookDao.update(favoriteBookEntity)

    fun getAllFavoriteBook(): LiveData<List<FavoriteBookEntity>> = fFavoriteBookDao.getAllFavoriteBook()

    //to convert the Int result (in Dao) into a Boolean
    suspend fun isFavorite(idBook: String): Boolean {
        return fFavoriteBookDao.isFavorite(idBook) > 0
    }

}