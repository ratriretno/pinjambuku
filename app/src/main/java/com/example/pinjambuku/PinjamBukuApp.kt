package com.example.pinjambuku

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pinjambuku.ui.BottomBar
import com.example.pinjambuku.ui.DetailScreen
import com.example.pinjambuku.ui.HomeScreen
import com.example.pinjambuku.ui.Profile
import com.example.pinjambuku.ui.navigation.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.ui.BorrowedBookScreen
import com.example.pinjambuku.ui.FavoriteScreen
import com.example.pinjambuku.ui.navigation.BookDestinations
import com.example.pinjambuku.ui.navigation.BookDestinationsArgs.BOOK_ID_ARG
import com.example.pinjambuku.ui.navigation.BookNavigationActions


@Composable
fun PinjamBukuApp(
    navController: NavHostController = rememberNavController(),
    navActions: BookNavigationActions = remember(navController) {
        BookNavigationActions(navController)
    }
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // daftar route BottomBar
    val showBottomBarRoutes = listOf(
        Screen.Home.route,
        Screen.Favorite.route,
        Screen.BorrowedBook.route,
        Screen.Profile.route
    )

    Scaffold(
        //bottomBar = { BottomBar(navController = navController) },

        bottomBar = {
            if (currentRoute in showBottomBarRoutes) {
                BottomBar(navController = navController)
            }
        },
        modifier = Modifier) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier=Modifier.padding(paddingValues)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    navController= navController,
                    goToDetailBook = { book ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
                        navActions.navigateToDetail(book)
                    },
                )
            }

            composable(route = BookDestinations.DETAIL_ROUTE,
                arguments = listOf(
                    navArgument(BOOK_ID_ARG) { type = NavType.StringType }
                )
            ) {
                val book = navController.previousBackStackEntry?.savedStateHandle?.get<BookModel>("book")
                if (book != null) {
                    DetailScreen(navigateBack = { navController.popBackStack() }, book = book)
                }

            }


            composable(Screen.Favorite.route) {
                FavoriteScreen( navController = navController)

            }

            composable(Screen.BorrowedBook.route) {
                BorrowedBookScreen( navController = navController)
            }
            composable(Screen.Profile.route) {
                Profile(navController = navController)
            }
//            composable(Screen.Detail.route) {
//                DetailScreen(navController = navController)
//            }

        }

    }


}