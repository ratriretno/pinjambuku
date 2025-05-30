package com.example.pinjambuku.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.pinjambuku.data.SettingPreferences
import com.example.pinjambuku.database.ExampleBorrowedBookDao
import com.example.pinjambuku.database.ExampleBorrowedBookEntity
import com.example.pinjambuku.database.FavoriteBookDao
import com.example.pinjambuku.database.FavoriteBookEntity
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.network.ApiService
import com.example.pinjambuku.model.BooksResponse
import com.example.pinjambuku.model.BorrowResponse
import com.example.pinjambuku.model.LoginItem
import com.example.pinjambuku.model.LoginResponse
import com.example.pinjambuku.model.ProfileResponse
import com.example.pinjambuku.utils.AppExecutors
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.model.SignupItem

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
            result = ResultNetwork.Success(response.listBooks)
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
        }

        return result
    }

    suspend fun searchBooks(keyword : String): ResultNetwork<List<BookModel>> {
        result = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            val response = apiService.searchBooks(keyword)
            result = ResultNetwork.Success(response.listBooks)
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
        }

        return result
    }

    suspend fun listBorrowBooks(keyword : String): ResultNetwork<BooksResponse> {
        var result: ResultNetwork<BooksResponse> = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            val response = apiService.listBorrowBook(keyword)
            result = ResultNetwork.Success(response)
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
        }

        return result
    }

    suspend fun login(login : LoginItem): ResultNetwork<LoginResponse> {
        var resultLogin: ResultNetwork<LoginResponse> = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            val response = apiService.login(login.email, login.password)
            resultLogin = ResultNetwork.Success(response)
            Log.i("login", response.toString())
        } catch (e: Exception) {
            resultLogin = ResultNetwork.Error(e.message.toString())
            Log.i("login", e.message.toString())
        }

        return resultLogin
    }

    suspend fun profile(id: String): ResultNetwork<ProfileResponse> {
        var result: ResultNetwork<ProfileResponse> = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            val response = apiService.profile(id)
            result = ResultNetwork.Success(response)
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
            Log.i("login", e.message.toString())
        }

        return result
    }

    suspend fun borrow(idBook: String, idUser : String, bookName : String): ResultNetwork<BorrowResponse> {
        var result: ResultNetwork<BorrowResponse> = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            val response = apiService.borrowBook(idBook, idUser, bookName)
            result = ResultNetwork.Success(response)
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
            Log.i("borrow", e.message.toString())
        }

        return result
    }

    suspend fun returnBook(idBook: String, idTransaksi : String, bookName : String): ResultNetwork<BorrowResponse> {
        var result: ResultNetwork<BorrowResponse> = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            Log.i("repo", idTransaksi)
            val response = apiService.returnBook(idBook, idTransaksi)
            result = ResultNetwork.Success(response)
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
            Log.i("borrow", e.message.toString())
        }

        return result
    }

    suspend fun signup(item: SignupItem): ResultNetwork<LoginResponse> {
        var result: ResultNetwork<LoginResponse> = ResultNetwork.Loading
        try {
//            val response = apiService.getNews(BuildConfig.API_KEY)
            val response = apiService.signup(item.email, item.password, item.fullname, item.username, item.city)
            result = ResultNetwork.Success(response)
            Log.i("login", response.toString())
        } catch (e: Exception) {
            result = ResultNetwork.Error(e.message.toString())
            Log.i("login", e.message.toString())
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

    fun getLoginSetting(): LiveData<Boolean> {
        return pref.getLoginSetting().asLiveData()
    }

    fun getUserSetting(): LiveData<String> {
        return pref.getUserSetting().asLiveData()
    }

    suspend fun getLogin(): Boolean {
        return pref.getLogin()
    }

    suspend fun getUserId(): String {
        return pref.getUserId()
    }

    suspend fun saveLoginSetting(isLogin: Boolean, idUser : String) {
        pref.saveLogin(isLogin, idUser)
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