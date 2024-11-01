package com.bcan.sprintplanner.data.repositories

import com.bcan.sprintplanner.data.models.SprintModel
import com.bcan.sprintplanner.data.models.TaskModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getSprints(): Flow<NetworkResult<List<SprintModel>>>
    suspend fun getTasks(sprintId: String): Flow<NetworkResult<List<TaskModel>>>


}