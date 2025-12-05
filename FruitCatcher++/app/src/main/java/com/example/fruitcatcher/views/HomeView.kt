package com.example.fruitcatcher.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fruitcatcher.R
import com.example.fruitcatcher.SoundManager

@Composable
fun HomeView(
    modifier: Modifier = Modifier, 
    navController: NavController, 
    viewModel: HomeViewModel = hiltViewModel(),
    soundManager: SoundManager
) {
    val isLoggedIn by viewModel.isLoggedIn

    LaunchedEffect(Unit) {
        soundManager.playMenuMusic()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.inicio),
            contentDescription = "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nome),
                contentDescription = "Game Logo",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
            )
            Spacer(modifier = Modifier.height(64.dp))

            Image(
                painter = painterResource(id = R.drawable.button),
                contentDescription = "Start Game Button",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable { 
                        soundManager.playButtonClick()
                        if (isLoggedIn) {
                            navController.navigate("game") 
                        } else {
                            navController.navigate("login")
                        }
                    }
            )

            if (isLoggedIn) {
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Logout Button",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .clickable { 
                            soundManager.playButtonClick()
                            viewModel.logout() 
                        }
                )
            } else {
                Spacer(modifier = Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = "Login Button",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .clickable { 
                            soundManager.playButtonClick()
                            navController.navigate("login") 
                        }
                )
            }
        }
    }
}
