package com.example.movieexplorer.ui.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieexplorer.data.model.MovieSearchResult
import com.example.movieexplorer.data.model.MovieSummary
import com.example.movieexplorer.presentation.navigation.navigateToDetailsScreen
import com.example.movieexplorer.ui.shared_components.MoviePoster
import kotlin.math.min

@Composable
fun SearchResults(searchResult: MovieSearchResult, navController: NavController, showAllResultsClick: () -> Unit){
    Column(modifier = Modifier.fillMaxSize()) {
        for (i in 0..min(searchResult.movies.size, 5)-1) { // shows 5 search results at maximum
            val moviePreview = searchResult.movies[i]
            SearchResult(moviePreview, onClick = {
                navController.navigateToDetailsScreen(moviePreview.imdbId)
            })
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "View all ${searchResult.totalResults} results",
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable{
                    showAllResultsClick()
                }
            )
        }

    }
}

@Composable
private fun SearchResult(movie: MovieSummary, onClick: (id: String)->Unit){
    HorizontalDivider(thickness = 0.5.dp)
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        .clickable(enabled = true, onClick = {
            onClick(movie.imdbId)
        })
        .padding(horizontal = 15.dp, vertical = 5.dp)){
        Column(modifier = Modifier.weight(1f)) {
            Text(buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 18.sp, fontStyle = FontStyle.Italic)){
                    append(movie.title)
                }
                withStyle(SpanStyle(fontSize = 14.sp)){
                    append("\n${movie.year}")
                }
            }, lineHeight = 18.sp)
        }
        MoviePoster(movie.poster, id = "", width = 30.dp)
        Spacer(Modifier.width(10.dp))
    }
}