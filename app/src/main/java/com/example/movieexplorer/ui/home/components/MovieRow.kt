package com.example.movieexplorer.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.Resource
import com.example.movieexplorer.presentation.navigation.navigateToDetailsScreen
import com.example.movieexplorer.ui.shared_components.MoviePoster

@Composable
fun MovieRow(category: String, navController: NavController, moviesResource: Resource<List<Movie>>){

    Column(modifier = Modifier.height(200.dp).fillMaxWidth()) {
        Text(category, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(horizontal = 10.dp))
        Spacer(Modifier.height(10.dp))
        when(moviesResource){
            is Resource.Error -> {
                MovieRowError(moviesResource.message)
            }
            is Resource.Loading -> {
                MovieRowLoading()
            }
            is Resource.Success -> {
                MovieRowSuccess(moviesResource.data, navController)
            }
        }


    }
}

@Composable
private fun MovieRowError(errorMessage: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(errorMessage)
    }
}

@Composable
private fun MovieRowLoading() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        BallPulseProgressIndicator(modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
private fun MovieRowSuccess(movies: List<Movie>, navController: NavController) {
    if (movies.isEmpty()) {
        Spacer(Modifier.height(70.dp))
        Text(
            "Nothing here!",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.heightIn(min = 120.dp),
            contentPadding = PaddingValues(horizontal = 15.dp)
        ) {
            items(movies, key = { it.imdbId }) { movie ->
                MoviePoster(
                    movie.poster, movie.imdbId, movie.type, true,
                    onClick = { id ->
                        navController.navigateToDetailsScreen(id)
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}
