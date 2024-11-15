package com.bcan.sprintplanner.presentation.sprint

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bcan.sprintplanner.data.models.TaskModel
import com.bcan.sprintplanner.presentation.sprint.components.CreateTaskDialog
import com.bcan.sprintplanner.ui.SprintPlannerLoadingIndicator
import com.bcan.sprintplanner.ui.snackbar.SnackbarController
import com.bcan.sprintplanner.ui.snackbar.SnackbarEvent
import kotlinx.coroutines.launch

class SprintScreen(val sprintId: String) : Screen {

    @Composable
    override fun Content() {

        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SprintViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        var createTaskDialogVisibility by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) { viewModel.getTasks(sprintId) }

        if (uiState.isLoading) SprintPlannerLoadingIndicator()

        if (uiState.errorMessage.isNullOrEmpty().not()) {
            scope.launch { SnackbarController.sendEvent(SnackbarEvent(uiState.errorMessage!!)) }
        }

        if (createTaskDialogVisibility) {
            CreateTaskDialog(
                taskCode = viewModel.taskCode,
                summary = viewModel.summary,
                platform = viewModel.platform,
                storyPoint = viewModel.storyPoint,
                developmentPoint = viewModel.developmentPoint,
                testPoint = viewModel.testPoint,
                assignedTo = viewModel.assignedTo,
                notes = viewModel.notes,
                onAction = viewModel::onAction,
                onDismissRequest = { createTaskDialogVisibility = false },
                onCreateTask = {
                    viewModel.createTask(
                        sprintId = sprintId,
                        taskId = viewModel.taskCode,
                        taskModel = TaskModel(
                            taskId = viewModel.taskCode,
                            summary = viewModel.summary,
                            platform = viewModel.platform,
                            storyPoint = viewModel.storyPoint.toInt(),
                            developmentPoint = viewModel.developmentPoint.toInt(),
                            testPoint = viewModel.testPoint.toInt(),
                            assignedTo = viewModel.assignedTo,
                            notes = viewModel.notes
                        )
                    )
                    createTaskDialogVisibility = false
                })
        }

        Column {
            IconButton(
                onClick = { navigator.pop() }
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 16.dp).size(32.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.tasks.isNullOrEmpty() && !uiState.isLoading) {
                    Button(
                        shape = RoundedCornerShape(24.dp),
                        onClick = { createTaskDialogVisibility = true },
                        modifier = Modifier.padding(bottom = 128.dp).align(Alignment.Center)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Create First Task", style = MaterialTheme.typography.h4)
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Sprint Icon",
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }
                } else {
                    Text(uiState.tasks.toString())
                }
            }
        }
    }
}