package com.example.fruitcatcher.views

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _isLoggedIn = mutableStateOf(FirebaseAuth.getInstance().currentUser != null)
    val isLoggedIn: State<Boolean> = _isLoggedIn

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _isLoggedIn.value = false
    }
}
