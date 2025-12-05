package com.example.trabalhoapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trabalhoapi.sound.SoundManager
import com.example.trabalhoapi.ui.dogs.DogDetailView
import com.example.trabalhoapi.ui.dogs.DogListView
import com.example.trabalhoapi.ui.favorites.FavoritesScreen
import com.example.trabalhoapi.ui.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {

    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        soundManager = SoundManager(this)
        setContent {
            val navController = rememberNavController()
            val favoritesViewModel: FavoritesViewModel = viewModel()

            DisposableEffect(Unit) {
                onDispose {
                    soundManager.onDestroy()
                }
            }

            Scaffold(
                bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val items = listOf(Screen.Home, Screen.Favorites)
                    val showBottomBar = items.any { it.route == currentDestination?.route }

                    if (showBottomBar) {
                        soundManager.playBackgroundMusic()
                        NavigationBar {
                            items.forEach { screen ->
                                NavigationBarItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        soundManager.playButtonClickSound()
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "welcome",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("welcome") {
                        WelcomeScreen(navController, soundManager)
                    }
                    composable(Screen.Home.route) {
                        DogListView(navController, favoritesViewModel, soundManager)
                    }
                    composable(Screen.Favorites.route) {
                        FavoritesScreen(navController, favoritesViewModel, soundManager)
                    }
                    composable("dog/{breedName}") { backStackEntry ->
                        val breedName = backStackEntry.arguments?.getString("breedName") ?: ""
                        DogDetailView(breedName, navController, favoritesViewModel, soundManager)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        soundManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        soundManager.onResume()
    }
}