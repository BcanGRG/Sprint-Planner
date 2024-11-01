package com.bcan.sprintplanner.data.repositories

import com.bcan.sprintplanner.data.models.SprintModel
import com.bcan.sprintplanner.data.models.TaskModel
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepositoryImpl(
    private val firestore: FirebaseFirestore,
) : HomeRepository {

    override suspend fun getSprints(): Flow<NetworkResult<List<SprintModel>>> = flow {
        emit(NetworkResult.OnLoading)
        try {
            firestore.collection("Sprints").snapshots.collect { sprints ->
                val result = sprints.documents.map { it.data<SprintModel>() }
                emit(NetworkResult.OnSuccess(result, ""))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.OnError(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTasks(sprintId: String): Flow<NetworkResult<List<TaskModel>>> = flow {
        emit(NetworkResult.OnLoading)
        try {
            firestore.collection("Sprints").document(sprintId)
                .collection("Tasks").snapshots.collect { documents ->
                    val result = documents.documents.map { it.data<TaskModel>() }
                    emit(NetworkResult.OnSuccess(result, ""))
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.OnError(e.message))
        }
    }
}


sealed class NetworkResult<out T> {

    data class OnSuccess<out T>(val data: T?, val message: String?) : NetworkResult<T>()

    data class OnError(val message: String?) : NetworkResult<Nothing>()

    data object OnLoading : NetworkResult<Nothing>()
}
