package com.bcan.sprintplanner.data.repositories

import com.bcan.sprintplanner.data.models.NetworkResult
import com.bcan.sprintplanner.data.models.TaskModel
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class SprintRepositoryImpl(
    private val firestore: FirebaseFirestore,
) : SprintRepository {
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

    override suspend fun createTask(
        sprintId: String,
        taskModel: TaskModel
    ): Flow<NetworkResult<Any>> = flow {
        emit(NetworkResult.OnLoading)
        try {
            val taskId = generateTaskId()
            firestore.collection("Sprints").document(sprintId)
                .collection("Tasks").document(taskId)
                .set(taskModel.copy(taskId = taskId), merge = true)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.OnError(e.message))
        }
    }

    override suspend fun deleteTask(
        sprintId: String,
        taskId: String
    ): Flow<NetworkResult<Any>> = flow {
        emit(NetworkResult.OnLoading)
        try {
            firestore.collection("Sprints").document(sprintId)
                .collection("Tasks").document(taskId).delete()
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.OnError(e.message))
        }
    }

    override suspend fun updateTask(
        sprintId: String,
        taskId: String,
        taskModel: TaskModel
    ): Flow<NetworkResult<Any>> = flow {
        emit(NetworkResult.OnLoading)
        try {
            firestore.collection("Sprints").document(sprintId)
                .collection("Tasks").document(taskId).update(taskModel)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(NetworkResult.OnError(e.message))
        }
    }
}

fun generateTaskId(): String {
    return UUID.randomUUID().toString()
}