package com.bcan.sprintplanner.presentation.sprint

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bcan.sprintplanner.data.models.TaskModel
import com.bcan.sprintplanner.presentation.sprint.components.CreateTaskDialog
import com.bcan.sprintplanner.themes.onBackgroundLight
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
                            sprintId = sprintId,
                            taskId = viewModel.taskCode,
                            summary = viewModel.summary,
                            platform = viewModel.platform.name,
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

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navigator.pop() }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
                Text(
                    "Sprint-$sprintId",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.h4
                )
                if (!(uiState.tasks.isNullOrEmpty() && !uiState.isLoading)) {
                    Row(
                        modifier = Modifier.align(Alignment.TopEnd)
                            .clickable { createTaskDialogVisibility = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Task Icon",
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Create New Task",
                            style = MaterialTheme.typography.subtitle2,
                        )
                    }
                }
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
                } else TasksListSection(
                    tasks = uiState.tasks!!,
                    onRemoveTask = { taskId ->
                        viewModel.deleteTask(sprintId, taskId)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.TasksListSection(
    tasks: List<TaskModel>,
    onRemoveTask: (taskId: String) -> Unit
) {
    Surface(
        modifier = Modifier.align(Alignment.TopEnd).padding(vertical = 8.dp)
            .fillMaxWidth(0.75f).fillMaxHeight(0.7f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primary)
    ) {
        LazyColumn {
            stickyHeader {
                Row(
                    modifier = Modifier.background(MaterialTheme.colors.surface)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    HeaderText(text = "Task Code")
                    HeaderText(text = "Sprint")
                    HeaderText(text = "Platform")
                    HeaderText(text = "Summary", weight = 2f)
                    HeaderText(text = "Story Point")
                    HeaderText(text = "Development\nPoint")
                    HeaderText(text = "Test Point")
                    HeaderText(text = "Assigned")
                    HeaderText(text = "Notes")
                }
            }
            items(tasks) { task ->
                TaskCard(
                    modifier = Modifier.animateItem(),
                    task = task, onRemoveTask = onRemoveTask
                )
            }
        }
    }
}

@Composable
fun RowScope.HeaderText(
    text: String,
    color: Color = Color.Transparent,
    weight: Float = 1f
) {
    Text(
        text = text,
        modifier = Modifier.background(color).weight(weight),
        color = onBackgroundLight,
        style = MaterialTheme.typography.caption,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RowScope.TaskText(
    text: String,
    weight: Float = 1f,
    color: Color = Color.Transparent,
    align: TextAlign = TextAlign.Center
) {
    Text(
        text = text,
        modifier = Modifier.background(color).weight(weight),
        fontSize = 12.sp,
        color = MaterialTheme.colors.secondary,
        textAlign = align
    )
}

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: TaskModel?,
    onRemoveTask: (taskId: String) -> Unit
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        shape = RectangleShape,
    ) {
        Icon(
            modifier = Modifier.padding(2.dp).size(16.dp)
                .clickable { onRemoveTask(task?.taskId.orEmpty()) },
            imageVector = Icons.Filled.Clear,
            contentDescription = "Delete Task",
            tint = MaterialTheme.colors.error
        )
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterHorizontally)
        ) {
            TaskText(text = task?.taskId.orEmpty())
            TaskText(text = "Sprint-${task?.sprintId}")
            TaskText(text = task?.platform.orEmpty(), align = TextAlign.Center)
            TaskText(
                text = task?.summary.orEmpty(),
                weight = 2f,
                align = TextAlign.Start
            )
            TaskText(text = task?.storyPoint.toString(), align = TextAlign.Center)
            TaskText(text = task?.developmentPoint.toString(), align = TextAlign.Center)
            TaskText(text = task?.testPoint.toString(), align = TextAlign.Center)
            TaskText(text = task?.assignedTo.orEmpty())
            TaskText(
                text = task?.notes.orEmpty(),
                align = TextAlign.Start
            )
        }
    }
}