package com.example.pinjambuku

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.pinjambuku.database.ExampleBorrowedBookEntity
import com.example.pinjambuku.database.FavoriteBookEntity
import com.example.pinjambuku.model.ExampleBook
import com.example.pinjambuku.model.ExampleBookData
import com.example.pinjambuku.model.FavoriteBook
import com.example.pinjambuku.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel (application: Application): AndroidViewModel(application){

    private val repository = BookRepository(application)

    // Borrowed book state (default false)
    var isBorrowed = mutableStateOf(false)
        private set

    

    //val favoriteBook: LiveData<List<ExampleBook>> = repository.getAllFavoriteBook()

    //favoriteMovies di bawah ini bertugas membaca data dari Room database
    val favoriteBook: LiveData<List<ExampleBook>> = repository.getAllFavoriteBook().map { entities ->
        entities.map { entity ->
            ExampleBook(
                idBuku = entity.idBuku.toInt(),
                judul = entity.judul ?: "",
                penulis = entity.penulis ?: "",
                penerbit = entity.penerbit ?: "",
                tahun = entity.tahun ?: "",
                isbn = entity.isbn ?: "",
                kategori = entity.kategori ?: "",
                pemilik = entity.pemilik ?: "",
                image = entity.image ?: R.drawable.placeholder,
                deskripsi = entity.deskripsi ?: ""
            )
        }
    }



    //borrowedBook di bawah ini bertugas membaca data dari Room database
    val borrowedBook: LiveData<List<ExampleBook>> = repository.getAllBorrowedBook().map { entities ->
        entities.map { entity ->
            ExampleBook(
                idBuku = entity.idBuku.toInt(),
                judul = entity.judul ?: "",
                penulis = entity.penulis ?: "",
                penerbit = entity.penerbit ?: "",
                tahun = entity.tahun ?: "",
                isbn = entity.isbn ?: "",
                kategori = entity.kategori ?: "",
                pemilik = entity.pemilik ?: "",
                image = entity.image ?: R.drawable.placeholder,
                deskripsi = entity.deskripsi ?: ""
            )
        }
    }

    val borrowedBookIds: LiveData<Set<Int>> = borrowedBook.map { list ->        // utk clickable or frozen item di homescreen
        list.map { it.idBuku }.toSet()
    }

/*
    // existing movie list
    var selectedMovie: Movie? by mutableStateOf(null)
*/
    // existing book list
    var selectedBook: ExampleBook? by mutableStateOf(null)



    // Search query state
    var searchQuery by mutableStateOf("")
        private set


//    private val _movies = MoviesData.movies                 // membaca semua object di "MoviesData"
    private  val _book = ExampleBookData.books              // membaca semua object di "ExampleBookData"


    // Favorite state (default false)
    var isFavorite = mutableStateOf(false)
        private set





    // Cek status favorite  jika suatu buku dipilih (dipinjam)
    fun checkIfFavorite(idBook: String) {
        viewModelScope.launch {
            isFavorite.value = repository.isFavorite(idBook)
        }
    }


    // cek status borrowed jika suatu buku dipilih(dipinjam)
    fun checkIfBorrowed(idBook: String) {
        viewModelScope.launch {
            isBorrowed.value = repository.isBorrowed(idBook)
        }
    }


    // Untuk toggle favorite state
    fun toggleFavorite() {
        //isFavorite.value = !isFavorite.value

        selectedBook?.let { book ->
            val favoriteBookEntity = FavoriteBookEntity(
                idBuku = book.idBuku.toString(),
                judul = book.judul,
                penulis = book.penulis,
                penerbit = book.penerbit,
                tahun = book.tahun,
                isbn = book.isbn,
                kategori = book.kategori,
                pemilik = book.pemilik,
                image = book.image,
                deskripsi = book.deskripsi
            )

            viewModelScope.launch {
                if (isFavorite.value) {
                    repository.delete(favoriteBookEntity)
                    isFavorite.value = false
                } else {
                    repository.insert(favoriteBookEntity)     // insert data to Room database
                    isFavorite.value = true
                }
            }
        }

    }


    // Untuk toggle borrowed state
    fun toggleBorrowed(){

        selectedBook?.let { book ->
            val exampleBorrowBookEntity = ExampleBorrowedBookEntity(
                idBuku = book.idBuku.toString(),
                judul = book.judul,
                penulis = book.penulis,
                penerbit = book.penerbit,
                tahun = book.tahun,
                isbn = book.isbn,
                kategori = book.kategori,
                pemilik = book.pemilik,
                image = book.image,
                deskripsi = book.deskripsi
            )

            viewModelScope.launch {
                if (isBorrowed.value) {
                    repository.delete(exampleBorrowBookEntity)      // delete data from Room database
                    isBorrowed.value = false
                } else {
                    repository.insert(exampleBorrowBookEntity)     // insert data to Room database
                    isBorrowed.value = true
                }
            }

        }

    }



    // Set favorite state based on database
    fun setFavorite(value: Boolean) {
        isFavorite.value = value
    }



    // Set borrowed book state based on database
    fun setBorrowed(value: Boolean){
        isBorrowed.value = value
    }

/*
    // Filtered movie list based on search
    val filteredMovies: List<ExampleBook>
        get() = if (searchQuery.isBlank()) {
            _movies
        } else {
            _movies.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.year.contains(searchQuery, ignoreCase = true)
            }
        }
*/

    // Filtered book list based on search
    val filteredBook: List<ExampleBook>
        get() = if (searchQuery.isBlank()) {
            _book
        } else {
            _book.filter {
                it.judul.contains(searchQuery, ignoreCase = true) ||
                        it.tahun.contains(searchQuery, ignoreCase = true) ||
                        it.penulis.contains(searchQuery, ignoreCase = true) ||
                        it.pemilik.contains(searchQuery, ignoreCase = true)
            }
        }

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }

}