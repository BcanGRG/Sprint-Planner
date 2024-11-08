package com.bcan.sprintplanner.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bcan.sprintplanner.data.models.SprintModel
import com.bcan.sprintplanner.presentation.sprint.SprintScreen
import com.bcan.sprintplanner.ui.SprintPlannerLoadingIndicator
import com.bcan.sprintplanner.ui.snackbar.SnackbarController
import com.bcan.sprintplanner.ui.snackbar.SnackbarEvent
import kotlinx.coroutines.launch

class HomeScreen : Screen {
    @Composable
    override fun Content() {

        val scope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        var addSprintDialogVisibility by remember { mutableStateOf(false) }

        if (uiState.isLoading) SprintPlannerLoadingIndicator()
        if (uiState.errorMessage.isNullOrEmpty().not()) {
            scope.launch { SnackbarController.sendEvent(SnackbarEvent(uiState.errorMessage!!)) }
        }

        if (addSprintDialogVisibility) {
            Dialog(
                onDismissRequest = { addSprintDialogVisibility = false },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = true,
                )
            ) {
                var sprintNumber by remember { mutableStateOf("") }
                var holiday by remember { mutableStateOf("0.0") }

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
                            Text("Sprint Number :", modifier = Modifier.weight(3f))
                            TextField(
                                value = sprintNumber,
                                onValueChange = { sprintNumber = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Holiday Count :", modifier = Modifier.weight(3f))
                            TextField(
                                value = holiday,
                                onValueChange = { holiday = it },
                                modifier = Modifier.weight(7f)
                            )
                        }

                        Button(onClick = {
                            if (viewModel.checkDocumentExists(sprintNumber.toInt())) {
                                scope.launch { SnackbarController.sendEvent(SnackbarEvent("Sprint already exists")) }
                            } else {
                                viewModel.createNewSprint(
                                    SprintModel(
                                        sprintNumber.toInt(),
                                        holiday.toDouble()
                                    )
                                )
                            }
                            addSprintDialogVisibility = false
                        }) {
                            Text("Create Sprint")
                        }
                    }
                }
            }
        }


        Box(
            modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                modifier = Modifier.clickable { addSprintDialogVisibility = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Sprint Icon",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Create New Sprint",
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }

        if (uiState.sprints.isNullOrEmpty().not()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize().padding(top = 48.dp),
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                items(uiState.sprints!!) { sprint ->
                    Card(
                        modifier = Modifier.padding(16.dp).height(150.dp)
                            .clickable { navigator.push(SprintScreen(sprintId = sprint.sprintId.toString())) },
                        shape = RoundedCornerShape(16.dp),
                        contentColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = 3.dp,
                        border = BorderStroke(2.dp, MaterialTheme.colors.secondary)
                    ) {
                        Box {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "Sprint ${sprint.sprintId}",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5, lineHeight = 0.sp
                            )
                            IconButton(
                                modifier = Modifier.padding(12.dp).size(30.dp)
                                    .align(Alignment.TopEnd),
                                onClick = {
                                    if (viewModel.checkDocumentExists(sprint.sprintId ?: 0)) {
                                        viewModel.deleteSprint(sprintId = sprint.sprintId.toString())
                                    } else scope.launch {
                                        SnackbarController.sendEvent(
                                            SnackbarEvent(
                                                "Document does not exist"
                                            )
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Remove Sprint Icon",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}