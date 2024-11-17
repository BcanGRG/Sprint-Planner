package com.bcan.sprintplanner.presentation.sprint

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import com.bcan.sprintplanner.ui.AssigneeFilterDropdownField
import com.bcan.sprintplanner.ui.PlatformFilterDropdownField
import com.bcan.sprintplanner.ui.PlatformTypes
import com.bcan.sprintplanner.ui.SprintPlannerLoadingIndicator
import com.bcan.sprintplanner.ui.TaskAction
import com.bcan.sprintplanner.ui.filteredAssignedList
import com.bcan.sprintplanner.ui.filteredPlatformList
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
        val tasks by viewModel.tasks.collectAsStateWithLifecycle(emptyList())

        var createTaskDialogVisibility by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) { viewModel.getTasks(sprintId) }

        if (uiState.isLoading) SprintPlannerLoadingIndicator()

        if (uiState.errorMessage.isNullOrEmpty().not()) {
            scope.launch { SnackbarController.sendEvent(SnackbarEvent(uiState.errorMessage!!)) }
        }

        var filteredPlatform by remember { mutableStateOf<PlatformTypes>(PlatformTypes.ALL) }
        var filteredAssignee by remember { mutableStateOf("All") }

        val taskModel by remember {
            derivedStateOf {
                TaskModel(
                    taskCode = viewModel.taskCode,
                    sprintId = sprintId,
                    summary = viewModel.summary,
                    platform = viewModel.platform.name,
                    storyPoint = viewModel.storyPoint.toInt(),
                    developmentPoint = viewModel.developmentPoint.toInt(),
                    testPoint = viewModel.testPoint.toInt(),
                    assignedTo = viewModel.assignedTo,
                    notes = viewModel.notes
                )
            }
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
                        taskModel = taskModel
                    )
                    createTaskDialogVisibility = false
                })
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
                        modifier = Modifier.align(Alignment.CenterEnd),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.clickable { createTaskDialogVisibility = true },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Add Task Icon",
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Create New Task",
                                style = MaterialTheme.typography.subtitle2,
                            )
                        }
                        Row(
                            modifier = Modifier.clickable {
                                filteredPlatform = PlatformTypes.ALL
                                filteredAssignee = "All"
                                viewModel.updatePlatformFilter(PlatformTypes.ALL)
                                viewModel.updateAssigneeFilter("All")
                            },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Clear Filters Icon",
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Clear All Filters",
                                style = MaterialTheme.typography.subtitle2,
                            )
                        }
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
                } else Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
                    ) {
                        PlatformFilterDropdownField(
                            value = filteredPlatform.name,
                            values = filteredPlatformList,
                            onClickDropdownItem = {
                                filteredPlatform = it
                                viewModel.updatePlatformFilter(it)
                            }
                        )
                        AssigneeFilterDropdownField(
                            value = filteredAssignee,
                            values = filteredAssignedList,
                            onClickDropdownItem = {
                                filteredAssignee = it
                                viewModel.updateAssigneeFilter(it)
                            }
                        )
                    }

                    TasksListSection(
                        tasks = tasks,
                        onAction = viewModel::onUpdateFieldAction,
                        onRemoveTask = { taskId ->
                            viewModel.deleteTask(sprintId, taskId)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TasksListSection(
    tasks: List<TaskModel>,
    onAction: (TaskAction) -> Unit,
    onRemoveTask: (taskId: String) -> Unit
) {
    Surface(
        modifier = Modifier.align(Alignment.End).padding(vertical = 8.dp)
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
                    HeaderText(text = "", weight = 0.3f)
                    HeaderText(text = "Task Code")
                    HeaderText(text = "Sprint")
                    HeaderText(text = "Platform")
                    HeaderText(text = "Summary", weight = 2f)
                    HeaderText(text = "Story Point")
                    HeaderText(text = "Development\nPoint")
                    HeaderText(text = "Test Point")
                    HeaderText(text = "Assigned To")
                    HeaderText(text = "Notes")
                }
            }
            items(tasks) { task ->
                TaskCard(
                    modifier = Modifier.animateItem(),
                    task = task, onAction = onAction, onRemoveTask = onRemoveTask
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
    modifier: Modifier = Modifier,
    text: String,
    weight: Float = 1f,
    textColor: Color = MaterialTheme.colors.secondary,
    align: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        modifier = Modifier.weight(weight).then(modifier),
        fontSize = 12.sp,
        lineHeight = 15.sp,
        color = textColor,
        textAlign = align,
    )
}

@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    task: TaskModel?,
    onAction: (TaskAction) -> Unit,
    onRemoveTask: (taskId: String) -> Unit
) {
    Card(
        modifier = modifier,
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        shape = RectangleShape,
    ) {
        Row {
            Icon(
                modifier = Modifier.padding(start = 6.dp, 4.dp).size(18.dp)
                    .clickable { onRemoveTask(task?.taskId.orEmpty()) },
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit Task",
                tint = MaterialTheme.colors.primary
            )
            Icon(
                modifier = Modifier.padding(start = 2.dp, 4.dp).size(18.dp)
                    .clickable { onRemoveTask(task?.taskId.orEmpty()) },
                imageVector = Icons.Filled.Clear,
                contentDescription = "Delete Task",
                tint = MaterialTheme.colors.error
            )
        }
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterHorizontally)
        ) {
            TaskText(text = "", weight = 0.3f)
            TaskText(text = task?.taskCode.orEmpty())
            TaskText(text = "Sprint-${task?.sprintId}")
            TaskText(
                text = task?.platform.orEmpty(),
                modifier = Modifier.background(
                    MaterialTheme.colors.secondary,
                    RoundedCornerShape(16.dp)
                ).padding(vertical = 8.dp),
                textColor = MaterialTheme.colors.onPrimary
            )
            TaskText(text = task?.summary.orEmpty(), weight = 2f)
            TaskText(text = task?.storyPoint.toString())
            TaskText(text = task?.developmentPoint.toString())
            TaskText(text = task?.testPoint.toString())
            TaskText(text = task?.assignedTo.orEmpty())
            TaskText(text = task?.notes.orEmpty())
        }
    }
}