package com.bcan.sprintplanner.ui

sealed class PlatformTypes(val name: String) {
    data object Unknown : PlatformTypes(name = "Unknown")
    data object AND : PlatformTypes(name = "Android")
    data object IOS : PlatformTypes(name = "Ios")
    data object TEST : PlatformTypes(name = "Test")
}

sealed interface UiAction {
    data class UpdateTaskCode(val taskCode: String) : UiAction
    data class UpdateSummary(val summary: String) : UiAction
    data class UpdatePlatform(val platform: PlatformTypes) : UiAction
    data class UpdateStoryPoint(val storyPoint: String) : UiAction
    data class UpdateDevelopmentPoint(val developmentPoint: String) : UiAction
    data class UpdateTestPoint(val testPoint: String) : UiAction
    data class UpdateAssignedTo(val assignedTo: String) : UiAction
    data class UpdateNotes(val notes: String) : UiAction
}

val platformList = listOf(PlatformTypes.AND, PlatformTypes.IOS, PlatformTypes.TEST)
val pointsList = listOf("1", "2", "3", "5", "8", "13", "21", "34")
val assignedList = listOf(
    "Unassigned",
    "Ali Han",
    "Melisa",
    "Cihan",
    "Tarık",
    "Burak Can",
    "Tuğba",
    "Mücahit",
    "Azime",
    "Yağmur",
    "Burak",
    "Davut"
)