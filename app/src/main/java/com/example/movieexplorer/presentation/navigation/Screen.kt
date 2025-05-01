package com.example.movieexplorer.presentation.navigation

sealed class Screen(val title: String, val route: String) {
    data object HomeScreen : Screen("Home", "home")
    data object SearchScreen : Screen("Search", "search")
    data object DetailsScreen : Screen("Details", "details")
}