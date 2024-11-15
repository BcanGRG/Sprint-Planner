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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bcan.sprintplanner.ui.NamedDropdownField
import com.bcan.sprintplanner.ui.NamedPlatformDropdownField
import com.bcan.sprintplanner.ui.NamedTextField
import com.bcan.sprintplanner.ui.PlatformTypes
import com.bcan.sprintplanner.ui.UiAction
import com.bcan.sprintplanner.ui.assignedList
import com.bcan.sprintplanner.ui.platformList
import com.bcan.sprintplanner.ui.pointsList

@Composable
fun CreateTaskDialog(
    taskCode: String,
    summary: String,
    platform: PlatformTypes,
    storyPoint: String,
    developmentPoint: String,
    testPoint: String,
    assignedTo: String,
    notes: String,
    onAction: (UiAction) -> Unit,
    onDismissRequest: () -> Unit,
    onCreateTask: () -> Unit,
) {
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
                    value = taskCode,
                    onValueChange = { onAction(UiAction.UpdateTaskCode(it)) }
                )

                NamedTextField(
                    fieldName = "Summary :",
                    value = summary,
                    onValueChange = { onAction(UiAction.UpdateSummary(it)) }
                )

                NamedPlatformDropdownField(
                    fieldName = "Platform :",
                    value = platform.name,
                    values = platformList,
                    onClickDropdownItem = { onAction(UiAction.UpdatePlatform(it)) }
                )

                NamedDropdownField(
                    fieldName = "Story Point :",
                    value = storyPoint,
                    values = pointsList,
                    onClickDropdownItem = { onAction(UiAction.UpdateStoryPoint(it)) }
                )

                NamedDropdownField(
                    fieldName = "Development Point :",
                    value = developmentPoint,
                    values = pointsList,
                    onClickDropdownItem = { onAction(UiAction.UpdateDevelopmentPoint(it)) }
                )

                NamedDropdownField(
                    fieldName = "Test Point :",
                    value = testPoint,
                    values = pointsList,
                    onClickDropdownItem = { onAction(UiAction.UpdateTestPoint(it)) }
                )

                NamedDropdownField(
                    fieldName = "Assigned To :",
                    value = assignedTo,
                    values = assignedList,
                    onClickDropdownItem = { onAction(UiAction.UpdateAssignedTo(it)) }
                )

                NamedTextField(
                    fieldName = "Notes :",
                    value = notes,
                    onValueChange = { onAction(UiAction.UpdateNotes(it)) }
                )

                Button(onClick = onCreateTask) { Text("Create Task") }
            }
        }
    }
}