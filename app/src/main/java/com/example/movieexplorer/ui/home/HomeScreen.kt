package com.example.movieexplorer.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieexplorer.presentation.viewmodel.AppViewModelProvider
import com.example.movieexplorer.presentation.viewmodel.HomeViewModel
import com.example.movieexplorer.ui.home.components.HomeSearchBar
import com.example.movieexplorer.ui.home.components.MovieRow

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        val recentMoviesState by viewModel.recentMoviesState.collectAsState()
        val favouriteMoviesState by viewModel.favouriteMoviesState.collectAsState()
        val moviesByRatingState   by viewModel.moviesByRatingState.collectAsState()

        Spacer(Modifier)
        HomeSearchBar(navController)

        MovieRow("Favourite", navController, favouriteMoviesState)
        HorizontalDivider(thickness = 0.2.dp)
        MovieRow("Viewed recently", navController, recentMoviesState)
        HorizontalDivider(thickness = 0.2.dp)
        MovieRow("Highest rated", navController, moviesByRatingState)
    }
}

