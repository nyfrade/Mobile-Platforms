package com.example.trabalhoapi.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trabalhoapi.FavoritesViewModel
import com.example.trabalhoapi.sound.SoundManager
import com.example.trabalhoapi.ui.dogs.DogGridItem

@Composable
fun FavoritesScreen(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    soundManager: SoundManager
) {
    val uiState by favoritesViewModel.uiState
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.error != null -> {
                Text(text = uiState.error!!, modifier = Modifier.padding(16.dp))
            }
            uiState.favorites.isEmpty() -> {
                Text(text = "No favorites yet!", modifier = Modifier.padding(16.dp))
            }
            else -> {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(uiState.favorites) { dog ->
                        DogGridItem(
                            dog = dog,
                            navController = navController,
                            favoritesViewModel = favoritesViewModel,
                            isFavorite = true,
                            soundManager = soundManager
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        favoritesViewModel.loadFavorites(context)
    }
}