package com.bcan.sprintplanner.data.repositories

import com.bcan.sprintplanner.data.models.NetworkResult
import com.bcan.sprintplanner.data.models.TaskModel
import kotlinx.coroutines.flow.Flow

interface SprintRepository {

    suspend fun getTasks(sprintId: String): Flow<NetworkResult<List<TaskModel>>>
    suspend fun createTask(
        sprintId: String,
        taskModel: TaskModel
    ): Flow<NetworkResult<Any>>

    suspend fun deleteTask(
        sprintId: String,
        taskId: String
    ): Flow<NetworkResult<Any>>

    suspend fun updateTask(
        sprintId: String,
        taskId: String,
        taskModel: TaskModel
    ): Flow<NetworkResult<Any>>
}