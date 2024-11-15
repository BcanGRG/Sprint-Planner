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
    val sprintId: String? = null,
    val summary: String? = null,
    val platform: String? = null,
    val storyPoint: Int? = 0,
    val developmentPoint: Int? = 0,
    val testPoint: Int? = 0,
    val assignedTo: String? = "Unassigned",
    val notes: String? = null,
)