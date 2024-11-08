package com.bcan.sprintplanner.presentation.sprint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bcan.sprintplanner.data.models.TaskModel
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

        var taskCode by remember { mutableStateOf("") }
        var summary by remember { mutableStateOf("") }
        var platform by remember { mutableStateOf("") }
        var storyPoint by remember { mutableStateOf("0") }
        var developmentPoint by remember { mutableStateOf("0") }
        var testPoint by remember { mutableStateOf("0") }
        var assignedTo by remember { mutableStateOf("Unassigned") }
        var notes by remember { mutableStateOf("") }

        if (uiState.isLoading) SprintPlannerLoadingIndicator()

        if (uiState.errorMessage.isNullOrEmpty().not()) {
            scope.launch { SnackbarController.sendEvent(SnackbarEvent(uiState.errorMessage!!)) }
        }

        if (createTaskDialogVisibility) {
            Dialog(
                onDismissRequest = { createTaskDialogVisibility = false },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = true,
                )
            ) {

                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                        .fillMaxWidth(0.9f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            24.dp,
                            alignment = Alignment.CenterVertically
                        ), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Task Code :", modifier = Modifier.weight(3f))
                            TextField(
                                value = taskCode,
                                onValueChange = { taskCode = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Summary :", modifier = Modifier.weight(3f))
                            TextField(
                                value = summary,
                                onValueChange = { summary = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Platform :", modifier = Modifier.weight(3f))
                            TextField(
                                value = platform,
                                onValueChange = { platform = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Story Point :", modifier = Modifier.weight(3f))
                            TextField(
                                value = storyPoint,
                                onValueChange = { storyPoint = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Development Point :", modifier = Modifier.weight(3f))
                            TextField(
                                value = developmentPoint,
                                onValueChange = { developmentPoint = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Test Point :", modifier = Modifier.weight(3f))
                            TextField(
                                value = testPoint,
                                onValueChange = { testPoint = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Assigned To :", modifier = Modifier.weight(3f))
                            TextField(
                                value = assignedTo,
                                onValueChange = { assignedTo = it },
                                modifier = Modifier.weight(7f)
                            )
                        }


                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Notes :", modifier = Modifier.weight(3f))
                            TextField(
                                value = notes,
                                onValueChange = { notes = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Button(onClick = {
                            viewModel.createTask(
                                sprintId, taskCode, TaskModel(
                                    taskId = taskCode,
                                    summary = summary,
                                    platform = platform,
                                    storyPoint = storyPoint.toInt(),
                                    developmentPoint = developmentPoint.toInt(),
                                    testPoint = testPoint.toInt(),
                                    assignedTo = assignedTo,
                                    notes = notes
                                )
                            )
                            createTaskDialogVisibility = false
                        }) { Text("Create Task") }
                    }
                }
            }
        }

        LaunchedEffect(Unit) { viewModel.getTasks(sprintId) }

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