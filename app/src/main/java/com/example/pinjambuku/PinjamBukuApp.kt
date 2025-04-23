package com.example.pinjambuku

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.rememberNavController
import com.example.pinjambuku.ui.BorrowedBookScreen
import com.example.pinjambuku.ui.FavoriteScreen


@Composable
fun PinjamBukuApp(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
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
                HomeScreen(viewModel = viewModel, navController= navController)
            }

            composable(Screen.Favorite.route) {
                FavoriteScreen(viewModel = viewModel, navController = navController)

            }

            composable(Screen.BorrowedBook.route) {
                BorrowedBookScreen(viewModel = viewModel, navController = navController)
            }
            composable(Screen.Profile.route) {
                Profile(navController = navController)
            }
            composable(Screen.Detail.route) {
                DetailScreen(viewModel = viewModel, navController = navController)
            }

        }

    }


}