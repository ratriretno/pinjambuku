package com.example.pinjambuku.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import com.example.myghibli.ui.theme.MyGhibliTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
//import com.example.myghibli.ui.theme.SkyBlue
import com.example.pinjambuku.R
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.network.ResultNetwork

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    login: Boolean,
    idUser: String,
    book: BookModel,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = LocalContext.current.let {
        ViewModelFactory.getInstance(
            LocalContext.current,
            it.dataStore
        )
    }),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigateToBorrow: () -> Unit,
    navigateToLogin: () -> Unit,

    ) {        //navController: NavHostController

    //var isBorrowed by remember { mutableStateOf(false) }

    val isBorrowed by viewModel.isBorrowed
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.collectAsState()//to check
    val books by viewModel.book.collectAsState()//to check

    viewModel.setBook(book)

    var enabled by remember { mutableStateOf(book.available) }
    var textButton by remember { mutableStateOf("Pinjam Buku") }

    Log.i("id u ser", idUser)
    Log.i("id borrow", book.idBorrower.toString())
    Log.i("id transaksi", book.idTransaksi.toString())

    var actionBuku= {viewModel.borrowBook(book.id.toString(), idUser, book.name.toString())}

    if (!books.available && idUser == book.idBorrower) {
        textButton = "Kembalikan Buku"
        enabled = true
        actionBuku = {
            Log.i("action", book.idTransaksi.toString())
            viewModel.returnBook(
            book.id.toString(),
            book.idTransaksi.toString(),
            book.name.toString()
        )}
    }


    viewModel.result.observe(lifecycleOwner) { result ->
        if (result != null) {
            when (result) {
                is ResultNetwork.Loading -> {
                    viewModel.setLoading(true)
                    Log.i("login update?", isLoading.toString())
                    enabled = false
                }

                is ResultNetwork.Success -> {
                    viewModel.setLoading(false)
//                    enabled=false
//
//                    viewModel.updateBook(idUser)
//
//                    Log.i("enable", books.available.toString())

                    Toast.makeText(
                        context,
                        result.data.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    navigateToBorrow()
//                    profile = result.data.profile

                }

                is ResultNetwork.Error -> {
                    viewModel.setLoading(false)
//                    enabled=true
//                    binding.progressBar2.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan" + result.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    //val movie = viewModel.selectedMovie


    //var isFavorite by remember { mutableStateOf(false) }

    val isFavorite by viewModel.isFavorite          // Observe favorite state

    //LaunchedEffect(movie?.id) {
    //    movie?.id?.let { viewModel.checkIfFavorite(it.toString()) }
    //}

//        LaunchedEffect(book?.idBuku) {
//            book?.idBuku?.let {
//                viewModel.checkIfBorrowed(it.toString())
//                viewModel.checkIfFavorite(it.toString())
//            }
//        }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Detail Buku") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {                        //navController.popBackStack()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            //Text(text = "Back")
                        }

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.hijau),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        bottomBar = {
            Button(

                onClick = {
                    if (login) {
                        actionBuku()
                    } else {
                        Toast.makeText(
                            context,
                            "Untuk meminjam buku harus login terlebih dahulu",
                            Toast.LENGTH_SHORT
                        ).show()

                        navigateToLogin()
                    }


                    //                        viewModel.toggleBorrowed()
                    //                        //isBorrowed = !isBorrowed  // Toggle state on click
                    //
                },
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBorrowed) colorResource(id = R.color.purple_500) else colorResource(
                        id = if (!books.available && idUser == book.idBorrower) {
                            R.color.kuning_kembali
                        } else {
                            R.color.hijau_muda
                        }

                    )
                )
            ) {
                Text(text = textButton)
            }
        }


    ) { innerPadding ->

        if (isLoading) {
            BigCircularLoadingLogin()
        } else {

            Column() {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {

                    //Main content
                    LazyColumn(
                        modifier = Modifier
                            //.padding(innerPadding)
                            .fillMaxSize()
                            //.background(SkyBlue)
                            .padding(16.dp)

                    ) {

                        item {

                            BookImageDetail(books)

//                                Image(
//                                    painter = painterResource(book.image),     //painterResource(movie.image)
//                                    contentDescription = book.judul,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .height(250.dp)
//                                        .clip(RoundedCornerShape(
//                                            topStart = 24.dp,
//                                            topEnd = 0.dp,
//                                            bottomEnd = 24.dp,
//                                            bottomStart = 0.dp)))

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    //.background(Color.Yellow)
                                    .fillMaxWidth()
                                //.padding(16.dp)
                                //.clip(RoundedCornerShape(30.dp))


                            ) {
                                Text(
                                    text = "${books.name}",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                        }
                        item {
                            Text(
                                text = "Penulis : ${books.writer}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            Text(text = "Tahun : ${books.year}")
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        /*
                                                item {
                                                    if (book != null) {
                                                        Text(text = "Penerbit : ${book.penerbit}")
                                                    }
                                                    Spacer(modifier = Modifier.height(8.dp))
                                                }

                         */

                        item {
                            Text(
                                text = "Deskripsi Buku",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${books.description}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }


                    }
                }
                FloatingActionButton(
                    onClick = {
//                            viewModel.toggleFavorite()
                    },
                    containerColor = Color(0xFFFFFFFF),
                    modifier = Modifier
//                    .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                        tint = Color(0xFFF540B2)
                    )
                }
            }
        }
    }
}

@Composable
fun BookImageDetail(book: BookModel) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center // Align to top-left
    ) {
        AsyncImage(
            model = book.photoUrl,
            contentScale = ContentScale.Fit,
            contentDescription = book.name,
            modifier = Modifier
                .size(200.dp)
        )
    }

}


@Composable
fun BigCircularLoadingDetail() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview(){
//    PinjamBukuTheme {
//        DetailScreen(viewModel = BookViewModel(application = Application()), navController = rememberNavController())
//    }
//}