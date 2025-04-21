package com.example.pinjambuku.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.pinjambuku.database.ExampleBookRoomDatabase
import com.example.pinjambuku.database.ExampleBorrowedBookDao
import com.example.pinjambuku.database.ExampleBorrowedBookEntity

class BookRepository (application: Application){

    private val eExampleBorrowedBookDao: ExampleBorrowedBookDao

    init {
        val db = ExampleBookRoomDatabase.getDatabase(application)
        eExampleBorrowedBookDao = db.exampleBorrowedBookDao()
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

}