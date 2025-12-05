package com.example.fruitcatcher.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    fun login(email: String, password: String): Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            auth.signInWithEmailAndPassword(email, password).await()
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)

    fun register(email: String, password: String): Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            auth.createUserWithEmailAndPassword(email, password).await()
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)
}
