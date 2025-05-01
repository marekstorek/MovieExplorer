package com.example.movieexplorer.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.MovieSearchResult
import com.example.movieexplorer.data.model.MovieSummary
import com.example.movieexplorer.data.model.Resource
import com.example.movieexplorer.data.model.toMoviePreview
import com.example.movieexplorer.presentation.navigation.navigateToDetailsScreen
import com.example.movieexplorer.presentation.viewmodel.AppViewModelProvider
import com.example.movieexplorer.presentation.viewmodel.SearchViewModel
import com.example.movieexplorer.ui.search.components.SearchBar
import com.example.movieexplorer.ui.search.components.SearchResults
import com.example.movieexplorer.ui.shared_components.MoviePoster

@Composable
fun SearchScreen(navController: NavController){

    val viewModel: SearchViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val searchState by viewModel.searchState.collectAsState()
    val recentMoviesState by viewModel.recentMoviesState.collectAsState()

    var showAllResults by rememberSaveable { mutableStateOf(false) } // Helper variable to decide whether all search results should be shown or just first ten

    Column {
        SearchBar(viewModel, onBackPressed = {
            if (showAllResults){
                showAllResults = false
            } else {
                navController.navigateUp()
            }
        })
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Spacer(Modifier)
            when(searchState){
                is Resource.Error -> SearchScreenError((searchState as Resource.Error).message)
                is Resource.Loading-> SearchScreenLoading()
                is Resource.Success -> {
                    val searchResult = (searchState as Resource.Success).data
                    SearchScreenSuccess(searchResult, showAllResults, navController, recentMoviesState, showAllResultsClick = {
                        viewModel.searchMovies(count = searchResult.totalResults) // ensures getting all available results from the server; up to 100
                        showAllResults = true
                    })
                }
            }
            Spacer(Modifier)
        }
    }
}

@Composable
fun RecentlyViewed(movies: List<MovieSummary>, navController: NavController){
    LazyColumn {
        items(movies) {movie->
            Row (modifier = Modifier.fillMaxWidth().clickable {
                navController.navigateToDetailsScreen(movie.imdbId)
            }.padding(horizontal = 10.dp, vertical = 5.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                MoviePoster(movie.poster, movie.imdbId, width = 50.dp, onClick = {})
                Text(movie.title, fontSize = 22.sp)
            }
        }
    }
    if (movies.isEmpty()){
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) { Text("Nothing there") }
    }
}

@Composable
private fun SearchScreenError(message: String) {
    Text(message, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 20.dp))
}

@Composable
private fun SearchScreenLoading(){
    BallPulseProgressIndicator(modifier = Modifier.padding(top = 20.dp))
}

@Composable
private fun SearchScreenSuccess(searchResult: MovieSearchResult, showAllResults: Boolean,
                                navController: NavController, recentMoviesState: Resource<List<Movie>>, showAllResultsClick: () -> Unit){
    if (searchResult.movies.isNotEmpty()){
        if (showAllResults){ // show all available results
            RecentlyViewed(
                movies = searchResult.movies,
                navController = navController)
        } else { // shows up to first five results
            SearchResults(
                searchResult = searchResult,
                navController = navController,
                showAllResultsClick = {
                    showAllResultsClick()
                })
        }
    } else {
        var movies: List<Movie> = emptyList()
        if (recentMoviesState is Resource.Success){
            movies = recentMoviesState.data
        }
        RecentlyViewed(movies.map { it.toMoviePreview() }, navController)
    }
}