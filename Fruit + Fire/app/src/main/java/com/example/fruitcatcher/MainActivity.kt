package com.example.fruitcatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruitcatcher.ui.login.LoginScreen
import com.example.fruitcatcher.views.GameOverView
import com.example.fruitcatcher.views.GameScreenView
import com.example.fruitcatcher.views.HomeView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeView(
                        modifier = Modifier, 
                        navController = navController,
                        soundManager = soundManager
                    )
                }
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        soundManager = soundManager
                    )
                }
                composable("game") {
                    GameScreenView(modifier = Modifier, navController = navController, soundManager = soundManager)
                }
                composable("gameOver") {
                    GameOverView(navController = navController)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        soundManager.stopMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.release()
    }
}
