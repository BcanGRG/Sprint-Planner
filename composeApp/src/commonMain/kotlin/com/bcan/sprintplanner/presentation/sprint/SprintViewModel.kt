package com.bcan.sprintplanner.presentation.sprint

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.bcan.sprintplanner.data.models.NetworkResult
import com.bcan.sprintplanner.data.models.TaskModel
import com.bcan.sprintplanner.data.repositories.SprintRepository
import com.bcan.sprintplanner.ui.PlatformTypes
import com.bcan.sprintplanner.ui.TaskAction
import com.bcan.sprintplanner.ui.UiAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SprintViewModel(
    private val sprintRepository: SprintRepository
) : ScreenModel {

    private val _uiState: MutableStateFlow<SprintUiState> = MutableStateFlow(SprintUiState())
    val uiState = _uiState.asStateFlow()

    var taskCode by mutableStateOf("")
        private set
    var summary by mutableStateOf("")
        private set
    var platform by mutableStateOf<PlatformTypes>(PlatformTypes.Unknown)
        private set
    var storyPoint by mutableStateOf("0")
        private set
    var developmentPoint by mutableStateOf("0")
        private set
    var testPoint by mutableStateOf("0")
        private set
    var assignedTo by mutableStateOf("Unassigned")
        private set
    var notes by mutableStateOf("")
        private set

    fun onAction(action: UiAction) {
        when (action) {
            is UiAction.UpdateTaskCode -> taskCode = action.taskCode
            is UiAction.UpdateSummary -> summary = action.summary
            is UiAction.UpdatePlatform -> platform = action.platform
            is UiAction.UpdateStoryPoint -> storyPoint = action.storyPoint
            is UiAction.UpdateDevelopmentPoint -> developmentPoint = action.developmentPoint
            is UiAction.UpdateTestPoint -> testPoint = action.testPoint
            is UiAction.UpdateAssignedTo -> assignedTo = action.assignedTo
            is UiAction.UpdateNotes -> notes = action.notes
        }
    }

    fun onUpdateFieldAction(
        action: TaskAction,
    ) {
        when (action) {
            is TaskAction.UpdateTaskFields -> {
                updateTask(
                    action.sprintId,
                    action.taskId,
                    action.taskModel ?: TaskModel(taskId = "MAPP-0")
                )
            }
        }
    }

    fun getTasks(sprintId: String) {
        screenModelScope.launch {
            sprintRepository.getTasks(sprintId).collectLatest { result ->
                when (result) {
                    is NetworkResult.OnLoading -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is NetworkResult.OnSuccess -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = null,
                                tasks = result.data
                            )
                        }
                    }

                    is NetworkResult.OnError -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun createTask(
        sprintId: String,
        taskModel: TaskModel
    ) {
        screenModelScope.launch {
            sprintRepository.createTask(sprintId, taskModel).collectLatest { result ->
                when (result) {
                    is NetworkResult.OnLoading -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is NetworkResult.OnSuccess -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = null,
                            )
                        }
                    }

                    is NetworkResult.OnError -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun deleteTask(
        sprintId: String,
        taskId: String,
    ) {
        screenModelScope.launch {
            sprintRepository.deleteTask(sprintId, taskId).collectLatest { result ->
                when (result) {
                    is NetworkResult.OnLoading -> {}

                    is NetworkResult.OnSuccess -> {}

                    is NetworkResult.OnError -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateTask(
        sprintId: String,
        taskId: String,
        taskModel: TaskModel
    ) {
        screenModelScope.launch {
            sprintRepository.updateTask(sprintId, taskId, taskModel).collectLatest { result ->
                when (result) {
                    is NetworkResult.OnLoading -> {}

                    is NetworkResult.OnSuccess -> {}

                    is NetworkResult.OnError -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }
}

data class SprintUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val tasks: List<TaskModel>? = emptyList(),
)