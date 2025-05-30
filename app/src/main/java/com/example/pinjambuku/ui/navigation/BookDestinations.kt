package com.example.pinjambuku.ui.navigation

import android.util.Log
import androidx.navigation.NavHostController
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.ui.navigation.BookDestinations.BORROW_ROUTE
import com.example.pinjambuku.ui.navigation.BookDestinations.HOME_ROUTE
import com.example.pinjambuku.ui.navigation.BookDestinations.LOGIN_ROUTE
import com.example.pinjambuku.ui.navigation.BookDestinations.SIGNUP_ROUTE
import com.example.pinjambuku.ui.navigation.BookDestinationsArgs.BOOK_ID_ARG
import com.example.pinjambuku.ui.navigation.BookDestinationsArgs.USER_ID_ARG
import com.example.pinjambuku.ui.navigation.BookScreens.ABOUT_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.BORROW_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.DETAIL_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.HOME_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.LOGIN_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.PROFILE_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.SIGNUP_SCREEN

private object BookScreens {
    const val HOME_SCREEN = "home"
    const val DETAIL_SCREEN = "detail"
    const val ABOUT_SCREEN = "about"
    const val PROFILE_SCREEN = "profile"
    const val LOGIN_SCREEN = "login"
    const val SIGNUP_SCREEN = "signup"
    const val BORROW_SCREEN = "borrowedbook"
}

object BookDestinationsArgs {
    const val BOOK_ID_ARG = "idBook"
    const val USER_ID_ARG = "idUser"
}

object BookDestinations {
    const val HOME_ROUTE = HOME_SCREEN
    const val DETAIL_ROUTE = "$DETAIL_SCREEN/{$BOOK_ID_ARG}"
    const val PROFILE_ROUTE = "${PROFILE_SCREEN}/{$USER_ID_ARG}"
    const val ABOUT_ROUTE = ABOUT_SCREEN
    const val LOGIN_ROUTE = LOGIN_SCREEN
    const val SIGNUP_ROUTE = SIGNUP_SCREEN
    const val BORROW_ROUTE = BORROW_SCREEN
}

class BookNavigationActions(private val navController: NavHostController) {
    fun navigateToDetail(book: BookModel) {
        navController.navigate("$DETAIL_SCREEN/${book.id}")
    }

    fun navigateToProfile(id : String) {
        Log.i("navigate", id)
        navController.navigate("$PROFILE_SCREEN/${id}")
    }

    fun navigateToLogin() {
        navController.navigate(LOGIN_ROUTE)
    }

    fun navigateToSignup(){
        navController.navigate(SIGNUP_ROUTE)
    }

    fun navigateToHome(){
        navController.navigate(HOME_ROUTE)
    }

    fun navigateToBorrow(){
        navController.navigate(BORROW_ROUTE)
    }
}
