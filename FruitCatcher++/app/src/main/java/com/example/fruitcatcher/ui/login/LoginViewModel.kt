package com.example.fruitcatcher.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruitcatcher.repositories.AuthRepository
import com.example.fruitcatcher.repositories.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login(onAuthSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.login(_uiState.value.email, _uiState.value.password).collect { result ->
                handleAuthResult(result, onAuthSuccess)
            }
        }
    }

    fun register(onAuthSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.register(_uiState.value.email, _uiState.value.password).collect { result ->
                handleAuthResult(result, onAuthSuccess)
            }
        }
    }

    private fun handleAuthResult(result: ResultWrapper<Unit>, onAuthSuccess: () -> Unit) {
        when (result) {
            is ResultWrapper.Loading -> {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            }
            is ResultWrapper.Success -> {
                _uiState.value = _uiState.value.copy(isLoading = false)
                onAuthSuccess()
            }
            is ResultWrapper.Error -> {
                _uiState.value = _uiState.value.copy(isLoading = false, error = result.message)
            }
        }
    }
}
