package com.example.pinjambuku.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pinjambuku.BookViewModel
import com.example.pinjambuku.model.ExampleBook
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.pinjambuku.ui.theme.PinjamBukuTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowedBookScreen(
    viewModel: BookViewModel,
    navController: NavHostController
){
    val borrowedBook by viewModel.borrowedBook.observeAsState(emptyList())

    if (borrowedBook.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            Scaffold (
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = "Buku Yang Dipinjam") },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
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
                            containerColor = Color(0xFF2196F3),
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        )
                    )
                },
            ){paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = "Tidak ada buku yang dipinjam",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    } else {
        // Show favorite list
        BorrowedBookList(
            books = borrowedBook,
            navController = navController,
            viewModel = viewModel
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowedBookList(                               //bertugas mengambil data Room database yang sudah dibaca oleh borrowedBook: LiveData<List<ExampleBook>> = repository.getAllBorrowedBook().map (di ViewModel)
    books: List<ExampleBook>,
    navController: NavHostController,
    viewModel: BookViewModel,
    modifier: Modifier = Modifier
) {

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Buku Yang Dipinjam") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(books, key = { it.idBuku }) { book ->
                BorrowBookListItem(
                    image = book.image,
                    title = book.judul,
                    year = book.tahun,
                    writer = book.penulis,
                    onClick = {
                        viewModel.selectedBook = book
                        navController.navigate("detail")        // Navigate to detail screen
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }

}


@Composable
fun BorrowBookListItem(
    image: Int,
    title: String,
    year: String,
    writer: String,
    onClick: () -> Unit,                    //spy bisa klik ke Detail
    modifier: Modifier
) {

    Card(modifier
        .padding(10.dp)
        .wrapContentSize()
        .clickable { onClick() },               //spy bisa klik ke Detail
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            //modifier = modifier.clickable {  }
        ){
            Image(painter = painterResource(id = image), contentDescription = title)
            Column (modifier.padding(12.dp)){
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        //.fillMaxWidth()
                        //.weight(1f)
                        .padding(start = 10.dp)
                )
                Text(
                    text = year,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        //.fillMaxWidth()
                        //.weight(1f)
                        .padding(start = 10.dp)
                )
                Text(
                    text = writer,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        //.fillMaxWidth()
                        //.weight(1f)
                        .padding(start = 10.dp)
                )
            }


        }

    }

}

/*
@Preview(showBackground = true)
@Composable
fun BorrowBookScreenPreview(){
    PinjamBukuTheme {
        BorrowBookScreen(viewModel = BookViewModel(application = Application()) , navController = rememberNavController())
    }
}

 */