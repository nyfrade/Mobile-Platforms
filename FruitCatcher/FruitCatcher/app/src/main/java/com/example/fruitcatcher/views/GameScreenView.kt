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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.fruitcatcher.GameView

@Composable
fun GameScreenView(modifier: Modifier, navController: NavController) {
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
                onGameOver = {
                    navController.navigate("gameOver/$it")
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
