package com.example.pinjambuku.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pinjambuku.BookViewModel
import com.example.pinjambuku.R
import com.example.pinjambuku.model.BottomBarItem
import com.example.pinjambuku.model.ExampleBook
import com.example.pinjambuku.ui.navigation.Screen
import com.example.pinjambuku.ui.theme.PinjamBukuTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import androidx.compose.material.icons.filled.MenuBook // or Book, LibraryBooks, etc.
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.LiveData
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.ui.screen.HomeViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel(factory = LocalContext.current.let {
        ViewModelFactory.getInstance(
            LocalContext.current,
            it.dataStore
        )
    })
) {


    val borrowedIds by viewModel.borrowedBookIds.observeAsState(emptySet())             // utk clickable or frozen item di homescreen
    //Scaffold() {innerPadding ->
        Column(
            modifier = Modifier
            //.verticalScroll(rememberScrollState())
                .fillMaxSize()
            //.padding(innerPadding)
        ) {
            Banner()

                    //c
            SearchBar(
                query = viewModel.searchQuery,
                onQueryChanged = { viewModel.updateSearchQuery(it) }
            )


            // Movie list based on search
            BookList(
                books = viewModel.filteredBook,
                borrowedBookIds = borrowedIds,              // utk clickable or frozen item di homescreen
                navController = navController,
                viewModel = viewModel)

        }

}



@Composable
fun Banner(modifier: Modifier = Modifier){
    Box(){
        Image(painter = painterResource(R.drawable.banner) ,contentDescription = "banner", contentScale = ContentScale.Crop, modifier = Modifier.height(250.dp))
    }

}


@Composable
fun BookList(
    books: List<ExampleBook>,
    borrowedBookIds: Set<Int>,                  // utk clickable or frozen item di homescreen
    navController: NavHostController,           //spy bisa klik ke Detail
    viewModel: BookViewModel,                 //spy bisa klik ke Detail
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){

        LazyColumn(modifier = Modifier.testTag("BookList")) {

            items(books, key={it.idBuku}){book ->
                BookListItem(
                    image = book.image,
                    title = book.judul,
                    year = book.tahun,
                    writer = book.penulis,
                    owner = book.pemilik,
                    isClickable = !borrowedBookIds.contains(book.idBuku),       // utk clickable or frozen item di homescreen
                    onClick = {                                                 //spy bisa klik ke Detail
                        viewModel.selectedBook = book                         //spy bisa klik ke Detail
                        navController.navigate(Screen.Detail.route)             //spy bisa klik ke Detail
                    },
                    modifier = Modifier
                        .fillMaxWidth()

                )
            }

        }

    }
}

@Composable
fun BookListItem(
    image: Int,
    title: String,
    year: String,
    writer: String,
    owner: String,
    isClickable: Boolean,
    onClick: () -> Unit,                    //spy bisa klik ke Detail
    modifier: Modifier
) {

    // val cardModifier di bawah ini utk clickable or frozen item di homescreen !!!
    val cardModifier = if (isClickable) {
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
                Image(painter = painterResource(id = image), contentDescription = title)
                Column(modifier
                    .padding(12.dp)
                    //.width(10.dp)
                ) {
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
                    Text(
                        text = "Pemilik: $owner",
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            //.fillMaxWidth()
                            //.weight(1f)
                            .padding(start = 10.dp)
                    )


                }

            }

            //Jika item un-clickable, ada tambahan badge "Dipinjam" disertai icon, jika item un-clickable
            if (!isClickable) {
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
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        label = { Text("Cari buku, penulis, tahun terbit, pemilik") },
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
fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorResource(id=R.color.hijau),
        modifier = modifier) {
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
                screen = Screen.Profile
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
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview(){
//    PinjamBukuTheme{
//        HomeScreen( viewModel = BookViewModel(application = Application()) , navController = rememberNavController())
//    }
//}