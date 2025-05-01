package com.example.movieexplorer.ui.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieexplorer.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchBar(viewModel: SearchViewModel, onBackPressed: () -> Unit){
    var query by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var lastRequestMillis: Long? by remember { mutableStateOf(null) }
    //val focusRequester = remember { FocusRequester() }

    //LaunchedEffect(Unit) {
    //    focusRequester.requestFocus()
   //     viewModel.clearSearchState()
    //}

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                coroutineScope.launch { // Eliminates needless calls after every key stroke
                    lastRequestMillis = System.currentTimeMillis()
                    delay(750)
                    if (System.currentTimeMillis() - lastRequestMillis!! > 650) {
                        viewModel.searchMovies(query)
                    }
                }
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp),
            modifier = Modifier
                .weight(1f)
                ,//.focusRequester(focusRequester),
            leadingIcon = {
                IconButton(onClick = {
                    onBackPressed()
                }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {}
            ),
            placeholder = { Text("Find a movie") }
        )
    }
}