package com.example.pinjambuku.ui.navigation

import androidx.navigation.NavHostController
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.ui.navigation.BookDestinationsArgs.BOOK_ID_ARG
import com.example.pinjambuku.ui.navigation.BookScreens.ABOUT_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.DETAIL_SCREEN
import com.example.pinjambuku.ui.navigation.BookScreens.HOME_SCREEN

private object BookScreens {
    const val HOME_SCREEN = "home"
    const val DETAIL_SCREEN = "detail"
    const val ABOUT_SCREEN = "about"
}

object BookDestinationsArgs {
    const val BOOK_ID_ARG = "idBook"
}

object BookDestinations {
    const val HOME_ROUTE = HOME_SCREEN
    const val DETAIL_ROUTE = "$DETAIL_SCREEN/{$BOOK_ID_ARG}"
    const val ABOUT_ROUTE = ABOUT_SCREEN
}

class BookNavigationActions(private val navController: NavHostController) {
    fun navigateToDetail(book: BookModel) {
        navController.navigate("$DETAIL_SCREEN/${book.id}")
    }

}