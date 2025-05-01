package com.example.pinjambuku

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pinjambuku.ui.BottomBar
import com.example.pinjambuku.ui.DetailScreen
import com.example.pinjambuku.ui.HomeScreen
import com.example.pinjambuku.ui.navigation.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pinjambuku.di.ViewModelFactory
import com.example.pinjambuku.model.BookModel
import com.example.pinjambuku.network.Constant.dataStore
import com.example.pinjambuku.ui.BorrowedBookScreen
import com.example.pinjambuku.ui.FavoriteScreen
import com.example.pinjambuku.ui.LoginScreen
import com.example.pinjambuku.ui.ProfileScreen
import com.example.pinjambuku.ui.SignUp
import com.example.pinjambuku.ui.navigation.BookDestinations
import com.example.pinjambuku.ui.navigation.BookDestinationsArgs.BOOK_ID_ARG
import com.example.pinjambuku.ui.navigation.BookDestinationsArgs.USER_ID_ARG
import com.example.pinjambuku.ui.navigation.BookNavigationActions


@Composable
fun PinjamBukuApp(
    navController: NavHostController = rememberNavController(),
    navActions: BookNavigationActions = remember(navController) {
        BookNavigationActions(navController)
    },
    viewModel: BookViewModel = viewModel(factory = LocalContext.current.let {
        ViewModelFactory.getInstance(
            LocalContext.current,
            it.dataStore
        )
    }),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,

){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isLogin by viewModel.isLogin.collectAsState()
    val idUser by viewModel.userId.collectAsState()

    // daftar route BottomBar
    val showBottomBarRoutes = listOf(
        Screen.Home.route,
        Screen.Favorite.route,
        Screen.BorrowedBook.route,
        Screen.Profile.route
    )

    viewModel.getLogin().observe(lifecycleOwner) { login: Boolean ->
            viewModel.setLogin(login)
    }

    viewModel.getUserIdSetting().observe(lifecycleOwner) { id: String ->
        viewModel.setUserId(id)
    }

    Scaffold(
        //bottomBar = { BottomBar(navController = navController) },

        bottomBar = {
            if (currentRoute in showBottomBarRoutes) {
                BottomBar(navController = navController,
                    viewModel = viewModel,
                    login= isLogin,
                    goToLogin = {navActions.navigateToLogin()},
                    goToProfile= { navActions.navigateToProfile(idUser) }
//                    goToProfile= {idUser ->
//                        navController.currentBackStackEntry?.savedStateHandle?.set("idUser", idUser)
//                        navActions.navigateToProfile(idUser)
//
//                    }
                )
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
                    DetailScreen(
                        navigateBack = { navController.popBackStack() },
                        book = book,
                        login= isLogin,
                        idUser = idUser)
                }

            }


            composable(Screen.Favorite.route) {
                FavoriteScreen( navController = navController)

            }

            composable(Screen.BorrowedBook.route) {
                BorrowedBookScreen(
                    idUser = idUser,
                    goToDetailBook = { book ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
                        navActions.navigateToDetail(book)
                    }
                    )
            }

            composable(route = BookDestinations.PROFILE_ROUTE,
                arguments = listOf(
                    navArgument(USER_ID_ARG) { type = NavType.StringType }
                )
            ) {
//                val id = navController.previousBackStackEntry?.savedStateHandle?.get<String>("idUser")
                    Log.i("compos", idUser)
                    ProfileScreen(navigateBack = {navActions.navigateToHome()}, idUser = idUser)

            }

            composable(route = BookDestinations.LOGIN_ROUTE
            ) {
                LoginScreen(
                    navigateBack = { navController.popBackStack() },
                    signUp = {navActions.navigateToSignup()},
                    goToProfile = { navActions.navigateToProfile(idUser) }
                    )
            }

            composable(route = BookDestinations.SIGNUP_ROUTE
            ) {
                SignUp(
                    navigateBack = { navController.popBackStack() },
                    goToProfile = { navActions.navigateToProfile(idUser) }
                )
            }
//            composable(Screen.Detail.route) {
//                DetailScreen(navController = navController)
//            }

        }

    }


}