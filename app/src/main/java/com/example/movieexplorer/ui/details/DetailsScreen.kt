package com.example.movieexplorer.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.example.movieexplorer.R
import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.Resource
import com.example.movieexplorer.presentation.viewmodel.AppViewModelProvider
import com.example.movieexplorer.presentation.viewmodel.MovieDetailsViewModel


@Composable
fun DetailsScreen(navController: NavController, id: String) {
    val viewModel: MovieDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)

    LaunchedEffect(id) {
        viewModel.getMovieById(id)
    }

    val movieState by viewModel.movieDetailsState.collectAsState()
    when (movieState){
        is Resource.Loading -> {
            Row (modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.Center){
                BallPulseProgressIndicator(modifier = Modifier.padding(top = 20.dp))
            }
        }
        is Resource.Error -> {
            Row (modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.Center){
                Text((movieState as Resource.Error<Movie>).message)
            }
        }
        is Resource.Success -> {
            val movie = (movieState as Resource.Success).data
            DetailsData(
                movie = movie,
                onBackPressed = {
                    navController.navigateUp()
                },
                onToggleMovie = {
                    viewModel.toggleMovieIsFavourite(movie)
                }
            )
        }
    }

}

@Composable
private fun DetailsData(movie: Movie, onBackPressed: ()-> Unit, onToggleMovie: () -> Unit){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .shadow(8.dp, RoundedCornerShape(16.dp))
        ) {
            DetailsImage(movie)

            IconButton(
                onClick = onToggleMovie,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(Color.White.copy(alpha = 0.8f), shape = CircleShape)
            ) {
                Icon(
                    imageVector = if (movie.isFavourite) Icons.Default.Star else Icons.TwoTone.Star,
                    contentDescription = "Favorite",
                    tint = if (movie.isFavourite) Color(0xFFFFC107) else Color(0xFF111111)
                )
            }

            IconButton(
                onClick = onBackPressed,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .background(Color.White.copy(alpha = 0.8f), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Favorite",
                    tint = Color(0xff000000)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${movie.title} (${movie.yearFrom}${movie.yearTo?.let { " - $it" } ?: ""})",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        movie.imdbRating?.let {
            Text(
                text = "IMDb: $it/10",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF616161)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        DetailSection(title = "Genres", content = movie.genre.joinToString(", "))
        DetailSection(title = "Director", content = movie.directors.joinToString(", "))
        DetailSection(title = "Writer", content = movie.writers.joinToString(", "))
        DetailSection(title = "Actors", content = movie.actors.joinToString(", "))
        DetailSection(title = "Languages", content = movie.language.joinToString(", "))
        DetailSection(title = "Countries", content = movie.country.joinToString(", "))

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.plot,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun DetailSection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF757575)
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Composable
private fun DetailsImage(movie: Movie) {
    AsyncImage(
        model = movie.poster,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
        placeholder = painterResource(R.drawable.gggr),
        error = painterResource(R.drawable.placeholder)
    )
}

