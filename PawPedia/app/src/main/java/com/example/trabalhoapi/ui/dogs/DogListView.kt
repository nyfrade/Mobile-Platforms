package com.example.trabalhoapi.ui.dogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trabalhoapi.R
import com.example.trabalhoapi.FavoritesViewModel
import com.example.trabalhoapi.sound.SoundManager

@Composable
fun DogListView(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    soundManager: SoundManager
) {
    val viewModel: DogListViewModel = viewModel()
    val dogListUiState by viewModel.uiState
    val favoritesUiState by favoritesViewModel.uiState
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn {
            items(dogListUiState.dogs) { dog ->
                val isFavorite = favoritesUiState.favorites.any { it.breedName == dog.breedName }
                DogGridItem(
                    dog = dog,
                    navController = navController,
                    favoritesViewModel = favoritesViewModel,
                    isFavorite = isFavorite,
                    soundManager = soundManager
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
        favoritesViewModel.loadFavorites(context)
    }
}