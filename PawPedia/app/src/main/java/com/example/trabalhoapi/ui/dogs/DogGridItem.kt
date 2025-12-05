package com.example.trabalhoapi.ui.dogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.trabalhoapi.FavoritesViewModel
import com.example.trabalhoapi.models.Dog
import com.example.trabalhoapi.sound.SoundManager

@Composable
fun DogGridItem(
    dog: Dog,
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    isFavorite: Boolean,
    soundManager: SoundManager
) {
    var isFav by remember { mutableStateOf(isFavorite) }
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { 
                soundManager.playButtonClickSound()
                navController.navigate("dog/${dog.breedName}") 
            }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
            ) {
                AsyncImage(
                    model = dog.imageUrl,
                    contentDescription = dog.breedName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                IconButton(
                    onClick = {
                        soundManager.playFavoriteSound()
                        isFav = !isFav
                        if (isFav) {
                            favoritesViewModel.addFavorite(context, dog)
                        } else {
                            favoritesViewModel.removeFavorite(context, dog)
                        }
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) Color.Red else Color.White
                    )
                }
            }
            Text(
                text = dog.breedName,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}