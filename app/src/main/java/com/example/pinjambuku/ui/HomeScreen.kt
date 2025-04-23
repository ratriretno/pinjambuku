package com.example.pinjambuku.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pinjambuku.R
import com.example.pinjambuku.model.BottomBarItem
import com.example.pinjambuku.ui.navigation.Screen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.compose.ui.draw.alpha
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.pinjambuku.BookViewModel
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.network.ResultNetwork
import com.example.pinjambuku.ui.screen.HomeViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(factory = LocalContext.current.let {
        ViewModelFactory.getInstance(
            LocalContext.current,
            it.dataStore
        )
    }),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    goToDetailBook: (BookModel) -> Unit,
) {

    val books = viewModel.bookList.collectAsStateWithLifecycle()
    val booksFirstLoad = viewModel.bookListAllFirstLoad.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsState()
    val query by viewModel.query.collectAsState()

    viewModel.result.observe(lifecycleOwner) { result ->
        if (result != null) {
            when (result) {
                is ResultNetwork.Loading -> {
                    viewModel.setLoading(true)
                    Log.i("loading update?", isLoading.toString())

                }

                is ResultNetwork.Success -> {
                    viewModel.setLoading(false)
                    viewModel.setBooks(result.data)
                    Log.i("books update?", books.value.toString())
                    Log.i("loading update?", isLoading.toString())

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

    ScrollItemHome(books, isLoading, query, viewModel, goToDetailBook)
}

@Composable
fun ScrollItemHome(
    books: State<List<BookModel>>,
    isLoading: Boolean,
    query: String,
    viewModel: HomeViewModel,
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
        Banner()

//        SearchBar()
        DecoratedTextField(query, viewModel)

        //c
//        SearchBar(
//            query = viewModel.searchQuery,
//            onQueryChanged = { viewModel.updateSearchQuery(it) }
//        )

        if (isLoading) {
            BigCircularLoading()
        } else {
            BookListHome(books, goToDetailBook)
        }

    }
}

@Composable
fun BigCircularLoading() {
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

@Composable
fun BookListHome(books: State<List<BookModel>>, goToDetailBook: (BookModel) -> Unit) {
    LazyColumn(modifier = Modifier.testTag("BookList")) {

        items(books.value) { book ->
            BookListItem(
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
fun Banner(modifier: Modifier = Modifier){
    Box(){
        Image(painter = painterResource(R.drawable.banner) ,contentDescription = "banner", contentScale = ContentScale.Crop, modifier = Modifier.height(250.dp))
    }

}

@Composable
fun BookListItem(
    book: BookModel,
    onClick: () -> Unit,                    //spy bisa klik ke Detail
    modifier: Modifier
) {

    Log.i("BookListItem", book.name.toString())
    Log.i("BookListItem", book.toString())

    // val cardModifier di bawah ini utk clickable or frozen item di homescreen !!!
    val cardModifier = if (book.available == true) {
        modifier
            .padding(10.dp)
            .wrapContentSize()
            .clickable { onClick() }
    } else {
        modifier
            .padding(10.dp)
            .wrapContentSize()
            .alpha(0.5f) // mengindikasikan jika disabled
    }

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

            //Jika item un-clickable, ada tambahan badge "Dipinjam" disertai icon, jika item un-clickable
            if (!book.available!!) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 10.dp)
                        .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Borrowed Icon",
                        tint = Color.Red,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Dipinjam",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun BookImage(book: BookModel){
    Box(
        modifier = Modifier.wrapContentSize(),
        contentAlignment = Alignment.TopStart // Align to top-left
    ) {
        AsyncImage(
            model = book.photoUrl,
            contentScale = ContentScale.Fit,
            contentDescription = book.name,
            modifier = Modifier
                .size(80.dp)
        )
    }

}

@Composable
fun SearchBar(
//    query: String,
//    onQueryChanged: (String) -> Unit
) {

    TextField(
        value = "",
        onValueChange = {},
        label = { Text("Cari nama buku atau penulis") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp, Color.DarkGray, RoundedCornerShape(30.dp)),
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black, fontSize = 20.sp
        )
    )
}

@Composable
fun DecoratedTextField(query: String, viewModel: HomeViewModel) {

    BasicTextField(
        value = query,
        onValueChange = { viewModel.setQuery(it)},
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 50.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(query.isEmpty()){
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }

                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Cari nama buku atau penulis",
                            style = TextStyle(color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
                if (query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.search()}) {
                        Icon(Icons.Default.Search, contentDescription = "Clear text")
                    }
                } else{

                }
            }
        },
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        )
    )
}



@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: BookViewModel,
    login: Boolean,
    goToLogin: () -> Unit,
    goToProfile:() ->Unit
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorResource(id=R.color.hijau),
        modifier = modifier) {

        val profileScreen : Screen = if(login){
            Screen.Profile
        } else {
            Screen.Login
        }

        val navigationItems = listOf(
            BottomBarItem(title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home

            ),
/*            BottomBarItem(title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),

 */
            BottomBarItem(title = stringResource(R.string.daftar_peminjaman),
                icon = Icons.Default.Book,
                screen = Screen.BorrowedBook
            ),
            BottomBarItem(title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            BottomBarItem(title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = profileScreen
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                modifier = Modifier.testTag(item.title),
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title)},
                //selected = item.title == navigationItems[0].title,
                selected = currentRoute == item.screen.route,
                onClick = {
                    if (currentRoute != item.screen.route) {
                        if(item.screen.route==profileScreen.route){
                            Log.i("item", item.screen.route)
                            Log.i("item", profileScreen.route)
                            Log.i("login bottom check", login.toString())
                            if (login){
                                //todo
                                goToProfile()
                            } else {
                                goToLogin()
                            }

                        } else{
                            navController.navigate(item.screen.route) {
                                // Pop up ke the start destination
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Prevent multiple copies
                                launchSingleTop = true
                                restoreState = true
                            }
                        }

                    }
                }
            )
        }
    }
}

fun search(){

}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview(){
//    PinjamBukuTheme{
//        HomeScreen( viewModel = BookViewModel(application = Application()) , navController = rememberNavController())
//    }
//}