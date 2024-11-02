package com.bcan.sprintplanner.data.repositories

import com.bcan.sprintplanner.data.models.NetworkResult
import com.bcan.sprintplanner.data.models.SprintModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getSprints(): Flow<NetworkResult<List<SprintModel>>>
    suspend fun createNewSprint(sprintId: String): Flow<NetworkResult<Any>>
    suspend fun deleteSprint(sprintId: String): Flow<NetworkResult<Any>>
}