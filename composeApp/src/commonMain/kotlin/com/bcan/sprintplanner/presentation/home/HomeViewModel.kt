package com.bcan.sprintplanner.presentation.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.bcan.sprintplanner.data.models.SprintModel
import com.bcan.sprintplanner.data.models.TaskModel
import com.bcan.sprintplanner.data.repositories.HomeRepository
import com.bcan.sprintplanner.data.repositories.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val homeRepository: HomeRepository
) : ScreenModel {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.onStart {
        getSprints()
        getTasks("Sprint 88")
    }.stateIn(
        scope = screenModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    fun getSprints() {
        screenModelScope.launch {
            homeRepository.getSprints().collectLatest { result ->
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
                                sprints = result.data
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

    fun getTasks(sprintId: String) {
        screenModelScope.launch {
            homeRepository.getTasks(sprintId).collectLatest { result ->
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

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val sprints: List<SprintModel>? = emptyList(),
    val tasks: List<TaskModel>? = emptyList(),
)