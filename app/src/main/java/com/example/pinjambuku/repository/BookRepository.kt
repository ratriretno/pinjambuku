package com.example.pinjambuku.repository

import androidx.lifecycle.LiveData
import com.example.pinjambuku.data.SettingPreferences
import com.example.pinjambuku.database.ExampleBorrowedBookDao
import com.example.pinjambuku.database.ExampleBorrowedBookEntity
import com.example.pinjambuku.database.FavoriteBookDao
import com.example.pinjambuku.database.FavoriteBookEntity
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.network.ApiService
import com.example.pinjambuku.utils.AppExecutors
import com.example.pinjambuku.network.ResultNetwork

class BookRepository(
    private val apiService: ApiService,
    private val bookBorrowDao: ExampleBorrowedBookDao,
    private val bookFavoriteDao: FavoriteBookDao,
    private val appExecutors: AppExecutors,
    private val pref: SettingPreferences
) {

    private lateinit var result: ResultNetwork<List<BookModel>>


    suspend fun getBooks(): ResultNetwork<List<BookModel>> {
        result = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            val response = apiService.getAllBooks()
            result = ResultNetwork.Success(response.listEvents)
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
        }

        return result
    }

    suspend fun insert(exampleBorrowedBookEntity: ExampleBorrowedBookEntity) =
        bookBorrowDao.insert(exampleBorrowedBookEntity)

    suspend fun delete(exampleBorrowedBookEntity: ExampleBorrowedBookEntity) =
        bookBorrowDao.delete(exampleBorrowedBookEntity)

    suspend fun update(exampleBorrowedBookEntity: ExampleBorrowedBookEntity) =
        bookBorrowDao.update(exampleBorrowedBookEntity)

    fun getAllBorrowedBook(): LiveData<List<ExampleBorrowedBookEntity>> =
        bookBorrowDao.getAllBorrowedBook()
    //fun getAllFavoriteMovie(): LiveData<List<Movie>> = mMovieDao.getAllFavoriteMovie()

    //to convert the Int result (in Dao) into a Boolean
    suspend fun isBorrowed(idBook: String): Boolean {
        return bookBorrowDao.isBorrowed(idBook) > 0
    }

    suspend fun insert(favoriteBookEntity: FavoriteBookEntity) =
        bookFavoriteDao.insert(favoriteBookEntity)

    suspend fun delete(favoriteBookEntity: FavoriteBookEntity) =
        bookFavoriteDao.delete(favoriteBookEntity)

    suspend fun update(favoriteBookEntity: FavoriteBookEntity) =
        bookFavoriteDao.update(favoriteBookEntity)

    fun getAllFavoriteBook(): LiveData<List<FavoriteBookEntity>> =
        bookFavoriteDao.getAllFavoriteBook()

    //to convert the Int result (in Dao) into a Boolean
    suspend fun isFavorite(idBook: String): Boolean {
        return bookFavoriteDao.isFavorite(idBook) > 0
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(
            apiService: ApiService,
            bookBorrowDao: ExampleBorrowedBookDao,
            bookFavoriteDao: FavoriteBookDao,
            appExecutors: AppExecutors,
            pref: SettingPreferences
        ): BookRepository =
            instance ?: synchronized(this) {
                instance ?: BookRepository(
                    apiService,
                    bookBorrowDao,
                    bookFavoriteDao,
                    appExecutors,
                    pref
                )
            }.also { instance = it }
    }

}