package com.bcan.sprintplanner.presentation.sprint.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bcan.sprintplanner.data.models.TaskModel
import com.bcan.sprintplanner.ui.NamedDropdownField
import com.bcan.sprintplanner.ui.NamedPlatformDropdownField
import com.bcan.sprintplanner.ui.NamedTextField
import com.bcan.sprintplanner.ui.assignedList
import com.bcan.sprintplanner.ui.platformList
import com.bcan.sprintplanner.ui.pointsList

@Composable
fun EditTaskDialog(
    task: TaskModel?,
    onDismissRequest: () -> Unit,
    onUpdateTask: (task: TaskModel) -> Unit,
) {

    var taskCode by remember { mutableStateOf(task?.taskCode) }
    var summary by remember { mutableStateOf(task?.summary) }
    var platform by remember { mutableStateOf(task?.platform) }
    var storyPoint by remember { mutableStateOf(task?.storyPoint.toString()) }
    var developmentPoint by remember { mutableStateOf(task?.developmentPoint.toString()) }
    var testPoint by remember { mutableStateOf(task?.testPoint.toString()) }
    var assignedTo by remember { mutableStateOf(task?.assignedTo) }
    var notes by remember { mutableStateOf(task?.notes) }


    Dialog(
        onDismissRequest = onDismissRequest,
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
                modifier = Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                verticalArrangement =
                Arrangement.spacedBy(24.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NamedTextField(
                    fieldName = "Task Code :",
                    value = taskCode.orEmpty(),
                    onValueChange = { taskCode = it }
                )

                NamedTextField(
                    fieldName = "Summary :",
                    value = summary.orEmpty(),
                    onValueChange = { summary = it }
                )

                NamedPlatformDropdownField(
                    fieldName = "Platform :",
                    value = platform.orEmpty(),
                    values = platformList,
                    onClickDropdownItem = { platform = it.name }
                )

                NamedDropdownField(
                    fieldName = "Story Point :",
                    value = storyPoint,
                    values = pointsList,
                    onClickDropdownItem = { storyPoint = it }
                )

                NamedDropdownField(
                    fieldName = "Development Point :",
                    value = developmentPoint,
                    values = pointsList,
                    onClickDropdownItem = { developmentPoint = it }
                )

                NamedDropdownField(
                    fieldName = "Test Point :",
                    value = testPoint,
                    values = pointsList,
                    onClickDropdownItem = { testPoint = it }
                )

                NamedDropdownField(
                    fieldName = "Assigned To :",
                    value = assignedTo.orEmpty(),
                    values = assignedList,
                    onClickDropdownItem = { assignedTo = it }
                )

                NamedTextField(
                    fieldName = "Notes :",
                    value = notes.orEmpty(),
                    onValueChange = { notes = it }
                )

                Button(
                    onClick = {
                        onUpdateTask(
                            TaskModel(
                                taskId = task?.taskId.orEmpty(),
                                taskCode = taskCode.orEmpty(),
                                sprintId = task?.sprintId.orEmpty(),
                                summary = summary.orEmpty(),
                                platform = platform.orEmpty(),
                                storyPoint = storyPoint.toInt(),
                                developmentPoint = developmentPoint.toInt(),
                                testPoint = testPoint.toInt(),
                                assignedTo = assignedTo.orEmpty(),
                                notes = notes.orEmpty()
                            )
                        )
                    }) { Text("Update Task") }
            }
        }
    }
}