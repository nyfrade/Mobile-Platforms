package com.example.fruitcatcher.views

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.fruitcatcher.GameView
import com.example.fruitcatcher.SoundManager

@Composable
fun GameScreenView(
    modifier: Modifier, 
    navController: NavController,
    soundManager: SoundManager
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) { 
        val context = LocalContext.current
        
        val screenWidthPx: Int
        val screenHeightPx: Int
        with(LocalDensity.current) {
            screenWidthPx = maxWidth.toPx().toInt()
            screenHeightPx = maxHeight.toPx().toInt()
        }

        val gameView = remember {
            GameView(context, 
                width = screenWidthPx, 
                height = screenHeightPx,
                soundManager = soundManager, 
                onGameOver = {
                    navController.navigate("gameOver")
                }
            )
        }

        val lifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(lifecycle) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> gameView.pause()
                    Lifecycle.Event.ON_RESUME -> gameView.resume()
                    else -> {}
                }
            }
            lifecycle.addObserver(observer)
            onDispose {
                lifecycle.removeObserver(observer)
            }
        }

        AndroidView(factory = { gameView })
    }
}
