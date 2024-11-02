package com.bcan.sprintplanner.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SprintModel(
    val sprintId: String? = null,
)

@Serializable
data class TaskModel(
    val taskId: String? = null,
    val summary: String? = null,
    val platform: String? = null,
)