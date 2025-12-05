package com.example.trabalhoapi.ui.dogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.trabalhoapi.R
import com.example.trabalhoapi.FavoritesViewModel
import com.example.trabalhoapi.sound.SoundManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogDetailView(
    breedName: String,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    soundManager: SoundManager
) {
    val viewModel: DogDetailViewModel = viewModel()
    val detailUiState by viewModel.uiState
    val favoritesUiState by favoritesViewModel.uiState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetch(breedName)
    }

    val dog = detailUiState.dog
    var isFavorite by remember(dog, favoritesUiState.favorites) { 
        mutableStateOf(dog != null && favoritesUiState.favorites.any { it.breedName == dog.breedName })
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().blur(radius = 16.dp),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = breedName) },
                    navigationIcon = {
                        IconButton(onClick = { 
                            soundManager.playButtonClickSound()
                            navController.popBackStack() 
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        if (dog != null) {
                            IconButton(onClick = {
                                soundManager.playFavoriteSound()
                                isFavorite = !isFavorite
                                if (isFavorite) {
                                    favoritesViewModel.addFavorite(context, dog)
                                } else {
                                    favoritesViewModel.removeFavorite(context, dog)
                                }
                            }) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            if (dog != null) {
                Card(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = dog.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}