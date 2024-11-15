package com.bcan.sprintplanner.presentation.sprint.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bcan.sprintplanner.ui.NamedDropdownField
import com.bcan.sprintplanner.ui.NamedPlatformDropdownField
import com.bcan.sprintplanner.ui.PlatformTypes
import com.bcan.sprintplanner.ui.UiAction
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
                        onValueChange = { onAction(UiAction.UpdateTaskCode(it)) },
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
                        onValueChange = { onAction(UiAction.UpdateSummary(it)) },
                        modifier = Modifier.weight(7f)
                    )
                }


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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Assigned To :", modifier = Modifier.weight(3f))
                    TextField(
                        value = assignedTo,
                        onValueChange = { onAction(UiAction.UpdateAssignedTo(it)) },
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
                        onValueChange = { onAction(UiAction.UpdateNotes(it)) },
                        modifier = Modifier.weight(7f)
                    )
                }

                Button(onClick = onCreateTask) { Text("Create Task") }
            }
        }
    }
}