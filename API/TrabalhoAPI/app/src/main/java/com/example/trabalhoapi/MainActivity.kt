package com.example.trabalhoapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabalhoapi.ui.theme.TrabalhoAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TrabalhoAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        startDestination = "dogs",
                        navController = navController
                    ) {
                        composable("dogs") {
                            DogListView(navController)
                        }
                        composable("dog/{breedName}") { backStackEntry ->
                            val breedName = backStackEntry.arguments?.getString("breedName") ?: ""
                            DogDetailView(breedName)
                        }
                    }
                }
            }
        }
    }
}