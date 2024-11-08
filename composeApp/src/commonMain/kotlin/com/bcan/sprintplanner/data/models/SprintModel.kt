package com.bcan.sprintplanner.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SprintModel(
    val sprintId: Int? = null,
    val holidayCount: Double? = 0.0,
)

@Serializable
data class TaskModel(
    val taskId: String? = null,
    val summary: String? = null,
    val platform: String? = null,
)