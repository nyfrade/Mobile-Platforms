package com.example.fruitcatcher.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GameOverView(modifier: Modifier = Modifier, navController: NavController, finalScore: Int) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Game Over", fontSize = 48.sp)
        Text(text = "Final Score: $finalScore", fontSize = 32.sp)
        Button(onClick = { 
            navController.navigate("home") { 
                popUpTo("home") { inclusive = true }
            } 
        }) {
            Text(text = "Play Again")
        }
    }
}