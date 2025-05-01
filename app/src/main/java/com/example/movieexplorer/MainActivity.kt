package com.example.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.movieexplorer.presentation.navigation.Navigation
import com.example.movieexplorer.ui.theme.MovieExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MovieExplorerTheme {
                SetSystemBarColours()

                Scaffold(modifier = Modifier.fillMaxSize().navigationBarsPadding().systemBarsPadding()) { pv ->
                    Navigation(navController, pv)
                }
            }
        }
    }
}

@Composable
private fun SetSystemBarColours() {
    val context = LocalContext.current
    val window = (context as ComponentActivity).window
    window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()//LocalExtraColors.current.topBar.toArgb()
    window.navigationBarColor = MaterialTheme.colorScheme.background.toArgb()//LocalExtraColors.current.bottomBar.toArgb()
    val useDarkIcons = MaterialTheme.colorScheme.background.luminance() > 0.5f

    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = useDarkIcons
        isAppearanceLightNavigationBars = useDarkIcons
    }
}

