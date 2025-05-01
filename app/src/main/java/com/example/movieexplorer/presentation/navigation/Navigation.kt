package com.example.movieexplorer.presentation.navigation

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieexplorer.ui.details.DetailsScreen
import com.example.movieexplorer.ui.home.HomeScreen
import com.example.movieexplorer.ui.search.SearchScreen

@Composable
fun Navigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.SearchScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it }, // Slide in from the bottom
                    animationSpec = tween(1000, delayMillis = 20)
                ) + fadeIn(animationSpec = tween(100, delayMillis = 200))
            }, popEnterTransition = {
                fadeIn(tween(1000))
            }, popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { it }, // Slide out to the bottom
                    animationSpec = tween(1000)
                )
                fadeOut(animationSpec = tween(1000))
            }) {
            SearchScreen(navController)
        }

        composable(
            route = Screen.DetailsScreen.route + "/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType }),
            enterTransition = {
                fadeIn(tween(700, easing = EaseIn))
            }, popExitTransition = {
                fadeOut(tween(800, easing = EaseOut))
            }
        ) {
            val movieId = it.arguments?.getString("movieId") ?: ""
            DetailsScreen(navController, id = movieId)
        }
    }
}

fun NavController.navigateToDetailsScreen(id: String) {
    this.navigate("${Screen.DetailsScreen.route}/${id}")
}

fun NavController.navigateToSearchScreen() {
    this.navigate(Screen.SearchScreen.route)
}




