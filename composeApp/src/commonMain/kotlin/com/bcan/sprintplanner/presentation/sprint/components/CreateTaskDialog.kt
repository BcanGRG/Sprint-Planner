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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bcan.sprintplanner.ui.PlatformTypes
import com.bcan.sprintplanner.ui.SelectionCard
import com.bcan.sprintplanner.ui.UiAction

@OptIn(ExperimentalMaterialApi::class)
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var isDropDownExpanded by remember { mutableStateOf(false) }
                    val list =
                        listOf(PlatformTypes.AND, PlatformTypes.IOS, PlatformTypes.TEST)
                    Text("Platform :", modifier = Modifier.weight(3f))
                    ExposedDropdownMenuBox(
                        modifier = Modifier.weight(7f),
                        expanded = isDropDownExpanded,
                        onExpandedChange = { isDropDownExpanded = !isDropDownExpanded }
                    ) {
                        SelectionCard(
                            value = platform.name,
                            onClick = { isDropDownExpanded = true }
                        )
                        DropdownMenu(
                            modifier = Modifier.exposedDropdownSize(),
                            expanded = isDropDownExpanded,
                            onDismissRequest = { isDropDownExpanded = false },
                        ) {
                            list.forEach {
                                DropdownMenuItem(
                                    enabled = true,
                                    onClick = {
                                        onAction(UiAction.UpdatePlatform(it))
                                        isDropDownExpanded = false
                                    }) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.name,
                                        fontSize = 15.sp,
                                        lineHeight = 20.sp, color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var isDropDownExpanded by remember { mutableStateOf(false) }
                    Text("Story Point :", modifier = Modifier.weight(3f))
                    val list =
                        listOf("1", "2", "3", "5", "8", "13", "21", "34")
                    ExposedDropdownMenuBox(
                        modifier = Modifier.weight(7f),
                        expanded = isDropDownExpanded,
                        onExpandedChange = { isDropDownExpanded = !isDropDownExpanded }
                    ) {
                        SelectionCard(
                            value = storyPoint,
                            onClick = { isDropDownExpanded = true }
                        )
                        DropdownMenu(
                            modifier = Modifier.exposedDropdownSize(),
                            expanded = isDropDownExpanded,
                            onDismissRequest = { isDropDownExpanded = false },
                        ) {
                            list.forEach {
                                DropdownMenuItem(
                                    enabled = true,
                                    onClick = {
                                        onAction(UiAction.UpdateStoryPoint(it))
                                        isDropDownExpanded = false
                                    }) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it,
                                        fontSize = 15.sp,
                                        lineHeight = 20.sp, color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Development Point :", modifier = Modifier.weight(3f))
                    TextField(
                        value = developmentPoint,
                        onValueChange = { onAction(UiAction.UpdateDevelopmentPoint(it)) },
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
                        onValueChange = { onAction(UiAction.UpdateTestPoint(it)) },
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