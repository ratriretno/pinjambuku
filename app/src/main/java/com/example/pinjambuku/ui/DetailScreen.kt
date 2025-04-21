package com.example.pinjambuku.ui

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.example.myghibli.ui.theme.MyGhibliTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pinjambuku.BookViewModel
//import com.example.myghibli.ui.theme.SkyBlue
import com.example.pinjambuku.R
import com.example.pinjambuku.ui.theme.PinjamBukuTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: BookViewModel,
    navController: NavHostController
) {        //navController: NavHostController

    //var isBorrowed by remember { mutableStateOf(false) }

    val isBorrowed by viewModel.isBorrowed
    val context = LocalContext.current


    //val movie = viewModel.selectedMovie
    val book = viewModel.selectedBook

    //if (movie != null){


        //var isFavorite by remember { mutableStateOf(false) }

        //val isFavorite by viewModel.isFavorite          // Observe favorite state

        //LaunchedEffect(movie?.id) {
        //    movie?.id?.let { viewModel.checkIfFavorite(it.toString()) }
        //}

        LaunchedEffect(book?.idBuku) {
            book?.idBuku?.let { viewModel.checkIfBorrowed(it.toString()) }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Detail Buku") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {                        //navController.popBackStack()
                            Row (verticalAlignment = Alignment.CenterVertically){
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
                        viewModel.toggleBorrowed()
                        //isBorrowed = !isBorrowed  // Toggle state on click
                        Toast.makeText(context, if (isBorrowed) "Buku dikembalikan!" else "Buku dipinjam!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp),
                    colors= ButtonDefaults.buttonColors(
                        containerColor = if (isBorrowed) colorResource(id = R.color.purple_500) else colorResource(id = R.color.hijau_muda) )
                ) {
                    Text(text = if (isBorrowed) "Kembalikan Buku" else "Pinjam Buku")
                }
            }


            ) { innerPadding ->

            Column() {

                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {

                    //Main content
                    LazyColumn(
                        modifier = Modifier
                            //.padding(innerPadding)
                            .fillMaxSize()
                            //.background(SkyBlue)
                            .padding(16.dp)

                    ) {
                        item{
                            if (book != null) {
                                Image(
                                    painter = painterResource(book.image),     //painterResource(movie.image)
                                    contentDescription = book.judul,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .height(250.dp)
                                        .clip(RoundedCornerShape(
                                            topStart = 24.dp,
                                            topEnd = 0.dp,
                                            bottomEnd = 24.dp,
                                            bottomStart = 0.dp)))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        item {
                            Box(modifier = Modifier
                                //.background(Color.Yellow)
                                .fillMaxWidth()
                                //.padding(16.dp)
                                //.clip(RoundedCornerShape(30.dp))


                            ){
                                Text(text = "${book?.judul}", style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                        }
                        item {
                            if (book != null) {
                                Text(text = "Penulis : ${book.penulis}",style = MaterialTheme.typography.titleMedium)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            if (book != null) {
                                Text(text = "Tahun : ${book.tahun}")
                            }
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
                        /*                    item {
                                                Text(text = "Producer: ")
                                                Text(text = "${movie?.producer}")
                                                Spacer(modifier = Modifier.height(8.dp))
                                            }

                         */
                        item {
                            Text(text = "Deskripsi Buku",style = MaterialTheme.typography.bodyLarge )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "${book?.deskripsi}", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(16.dp))
                        }



                    }


                }


            }


        }

    //}else{
    //    Text("Movie not found")
    //}

}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(){
    PinjamBukuTheme {
        DetailScreen(viewModel = BookViewModel(application = Application()), navController = rememberNavController())
    }
}