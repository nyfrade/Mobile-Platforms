package com.example.fruitcatcher.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.example.fruitcatcher.models.PlayerScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ScoreRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    fun add(score: PlayerScore): Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("scores")
                .add(score)
                .await()
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)

    fun getTopScores(limit: Long = 10): Flow<ResultWrapper<List<PlayerScore>>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .limit(limit)
                .snapshotFlow()
                .collect { snapshot ->
                    val scores = snapshot.documents.mapNotNull { doc ->
                        doc.toObject<PlayerScore>()?.apply {
                            docId = doc.id
                        }
                    }
                    emit(ResultWrapper.Success(scores))
                }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: ""))
        }
    }.flowOn(Dispatchers.IO)
}
