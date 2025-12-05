package com.example.trabalhoapi

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trabalhoapi.models.AppDatabase
import com.example.trabalhoapi.models.Dog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class FavoritesState(
    val favorites: List<Dog> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

class FavoritesViewModel : ViewModel() {

    var uiState = mutableStateOf(FavoritesState())
        private set

    fun loadFavorites(context: Context) {
        uiState.value = uiState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteDogs = AppDatabase.getInstance(context)?.dogDao()?.getAll()
            viewModelScope.launch(Dispatchers.Main) {
                uiState.value = uiState.value.copy(
                    favorites = favoriteDogs ?: emptyList(),
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    fun addFavorite(context: Context, dog: Dog) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(context)?.dogDao()?.insert(dog.copy(isFavorite = true))
            loadFavorites(context)
        }
    }

    fun removeFavorite(context: Context, dog: Dog) {
        viewModelScope.launch(Dispatchers.IO) {
            AppDatabase.getInstance(context)?.dogDao()?.delete(dog)
            loadFavorites(context)
        }
    }
}