package com.bcan.sprintplanner.presentation.sprint

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.bcan.sprintplanner.data.models.NetworkResult
import com.bcan.sprintplanner.data.models.TaskModel
import com.bcan.sprintplanner.data.repositories.SprintRepository
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
}

data class SprintUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val tasks: List<TaskModel>? = emptyList(),
)