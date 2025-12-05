package com.example.trabalhoapi

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screen("dogs", R.string.home, Icons.Default.Home)
    object Favorites : Screen("favorites", R.string.favorites, Icons.Default.Favorite)
}