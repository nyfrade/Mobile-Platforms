package com.example.fruitcatcher

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruitcatcher.ui.theme.FruitCatcherTheme
import com.example.fruitcatcher.views.GameOverView
import com.example.fruitcatcher.views.GameScreenView
import com.example.fruitcatcher.views.HomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            FruitCatcherTheme {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.fillMaxSize()
                ){
                    composable("home"){
                        HomeView(modifier = Modifier,
                            navController = navController
                        )
                    }
                    composable("game"){
                        GameScreenView(modifier = Modifier, navController = navController)
                    }
                    composable("gameOver/{score}") { backStackEntry ->
                        val score = backStackEntry.arguments?.getString("score")?.toInt() ?: 0
                        GameOverView(navController = navController, finalScore = score)
                    }
                }
            }
        }
    }
}
