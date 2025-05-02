package com.example.pinjambuku.ui.screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pinjambuku.R
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.network.ResultNetwork


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorrowedBookScreen(
    idUser: String,
    viewModel: BorrowBookViewModel = viewModel(factory = LocalContext.current.let {
        ViewModelFactory.getInstance(
            LocalContext.current,
            it.dataStore
        )
    }),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    goToDetailBook: (BookModel) -> Unit,
    navigateBack: () -> Unit,
){
    val books = viewModel.bookList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsState()

    viewModel.resultBorrow.observe(lifecycleOwner) { result ->
        if (result != null) {
            when (result) {
                is ResultNetwork.Loading -> {
                    viewModel.setLoading(true)
                    Log.i("loading update?", isLoading.toString())

                }

                is ResultNetwork.Success -> {
                    viewModel.setLoading(false)
                    viewModel.setBooks(result.data.listBooks)

                }

                is ResultNetwork.Error -> {
                    viewModel.setLoading(false)
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

    viewModel.listBorrowBook(idUser)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Peminjaman") },
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
                    ScrollItemBorrow (books, isLoading, viewModel, goToDetailBook)
                }
            }
        }
    }



}

@Composable
fun ScrollItemBorrow(
    books: State<List<BookModel>>,
    isLoading: Boolean,
    viewModel: BorrowBookViewModel,
    goToDetailBook: (BookModel) -> Unit
){
    //    val borrowedIds by viewModel.borrowedBookIds.observeAsState(emptySet())             // utk clickable or frozen item di homescreen
    //Scaffold() {innerPadding ->
    Column(
        modifier = Modifier
            //.verticalScroll(rememberScrollState())
            .fillMaxSize()
        //.padding(innerPadding)
    ) {


        if (isLoading) {
            BigCircularLoading()
        } else {
            BookListBorrow(books, goToDetailBook)
        }

    }
}

@Composable
fun BookListBorrow(books: State<List<BookModel>>, goToDetailBook: (BookModel) -> Unit) {
    LazyColumn(modifier = Modifier.testTag("BookList")) {

        items(books.value) { book ->
            BookListItemBorrow(
                book,
                onClick = { goToDetailBook(book)                                                //spy bisa klik ke Detail //spy bisa klik ke Detail
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
fun BookListItemBorrow(
    book: BookModel,
    onClick: () -> Unit,                    //spy bisa klik ke Detail
    modifier: Modifier
) {

    Log.i("BookListItem", book.name.toString())
    Log.i("BookListItem", book.toString())

    // val cardModifier di bawah ini utk clickable or frozen item di homescreen !!!
    val cardModifier =
        modifier
            .padding(10.dp)
            .wrapContentSize()
            .clickable { onClick() }

    Card(
        modifier = cardModifier,
        /*
        .padding(10.dp)
        .wrapContentSize()
        .clickable { onClick() },        //spy bisa klik ke Detail
*/
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {

        Column {
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 10.dp)
//                        .background(
////                            if (!book.endDate.isNullOrEmpty()){
//                                Color(0xB2E7EEE8)
////                            } else{
////                                Color(0xA3CDDC39)
////                            }
//                           , shape = RoundedCornerShape(8.dp))


                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                var textNotice : String

                if (!book.endDate.isNullOrEmpty()){
                    Log.i("end", book.endDate)
                    Log.i("end", book.name.toString())
                    textNotice = "Dikembalikan"
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Borrowed Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = textNotice,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                } else{
                    textNotice = "Dipinjam"
                    Icon(
                        imageVector = Icons.Default.AccessAlarm,
                        contentDescription = "Borrowed Icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = textNotice,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }



                }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,

                    //modifier = modifier.clickable {  }
                ) {
                    BookImage(book)
                    Column(modifier
                        .padding(12.dp)
                        //.width(10.dp)
                    ) {
                        Text(
                            text = book.name.toString(),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                //.fillMaxWidth()
                                //.weight(1f)
                                .padding(start = 5.dp)
                        )
                        Text(
                            text = book.year.toString(),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                //.fillMaxWidth()
                                //.weight(1f)
                                .padding(start = 10.dp)
                        )
                        Text(
                            text = book.writer.toString(),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                //.fillMaxWidth()
                                //.weight(1f)
                                .padding(start = 10.dp)
                        )
                        Text(
                            text = "Pemilik: ${book.ownerName.toString()}",
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                //.fillMaxWidth()
                                //.weight(1f)
                                .padding(start = 10.dp)
                        )


                    }

                }
        }


            //Jika item un-clickable, ada tambahan badge "Dipinjam" disertai icon, jika item un-clickable
//            if (!book.available!!) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .padding(top = 4.dp, start = 10.dp)
//                        .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(8.dp))
//                        .padding(horizontal = 8.dp, vertical = 4.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Lock,
//                        contentDescription = "Borrowed Icon",
//                        tint = Color.Red,
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = "Dipinjam",
//                        color = Color.Red,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 14.sp
//                    )
//                }
//            }
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