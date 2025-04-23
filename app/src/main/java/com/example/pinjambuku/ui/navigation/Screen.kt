package com.example.pinjambuku.ui.navigation

sealed class Screen (val route:String) {
    data object Home : Screen("home")
    data object Favorite : Screen("favorite")
    data object Profile : Screen("profile")
    data object Detail : Screen ("detail")
    data object BorrowedBook : Screen("borrowedbook")
    data object Login : Screen("login")
}