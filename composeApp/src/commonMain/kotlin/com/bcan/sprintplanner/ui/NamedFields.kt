package com.bcan.sprintplanner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NamedDropdownField(
    fieldName: String,
    value: String,
    values: List<String>,
    onClickDropdownItem: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isDropDownExpanded by remember { mutableStateOf(false) }

        Text(fieldName, modifier = Modifier.weight(3f))
        ExposedDropdownMenuBox(
            modifier = Modifier.weight(7f),
            expanded = isDropDownExpanded,
            onExpandedChange = { isDropDownExpanded = !isDropDownExpanded }
        ) {
            SelectionCard(
                value = value,
                onClick = { isDropDownExpanded = true }
            )
            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(),
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
            ) {
                values.forEach {
                    DropdownMenuItem(
                        enabled = true,
                        onClick = {
                            onClickDropdownItem(it)
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
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NamedPlatformDropdownField(
    fieldName: String,
    value: String,
    values: List<PlatformTypes>,
    onClickDropdownItem: (PlatformTypes) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isDropDownExpanded by remember { mutableStateOf(false) }

        Text(fieldName, modifier = Modifier.weight(3f))
        ExposedDropdownMenuBox(
            modifier = Modifier.weight(7f),
            expanded = isDropDownExpanded,
            onExpandedChange = { isDropDownExpanded = !isDropDownExpanded }
        ) {
            SelectionCard(
                value = value,
                onClick = { isDropDownExpanded = true }
            )
            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(),
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
            ) {
                values.forEach {
                    DropdownMenuItem(
                        enabled = true,
                        onClick = {
                            onClickDropdownItem(it)
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
}


@Composable
fun NamedTextField(
    fieldName: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(fieldName, modifier = Modifier.weight(3f))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(7f)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlatformFilterDropdownField(
    modifier: Modifier = Modifier,
    value: String,
    values: List<PlatformTypes>,
    onClickDropdownItem: (PlatformTypes) -> Unit,
) {

    var isDropDownExpanded by remember { mutableStateOf(false) }

    Column {
        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 3.dp),
            text = "Platform",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary
        )
        ExposedDropdownMenuBox(
            modifier = modifier.width(150.dp),
            expanded = isDropDownExpanded,
            onExpandedChange = { isDropDownExpanded = !isDropDownExpanded }
        ) {
            RoundedSelectionCard(
                value = value,
                onClick = { isDropDownExpanded = true }
            )
            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(),
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
            ) {
                values.forEach {
                    DropdownMenuItem(
                        enabled = true,
                        onClick = {
                            onClickDropdownItem(it)
                            isDropDownExpanded = false
                        }) {
                        Text(
                            text = it.name,
                            fontSize = 15.sp,
                            lineHeight = 20.sp, color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AssigneeFilterDropdownField(
    modifier: Modifier = Modifier,
    value: String,
    values: List<String>,
    onClickDropdownItem: (String) -> Unit,
) {

    var isDropDownExpanded by remember { mutableStateOf(false) }

    Column {
        Text(
            modifier = Modifier.padding(start = 3.dp, bottom = 3.dp),
            text = "Assigned To",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary
        )
        ExposedDropdownMenuBox(
            modifier = modifier.width(150.dp),
            expanded = isDropDownExpanded,
            onExpandedChange = { isDropDownExpanded = !isDropDownExpanded }
        ) {
            RoundedSelectionCard(
                value = value,
                onClick = { isDropDownExpanded = true }
            )
            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(),
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
            ) {
                values.forEach {
                    DropdownMenuItem(
                        enabled = true,
                        onClick = {
                            onClickDropdownItem(it)
                            isDropDownExpanded = false
                        }) {
                        Text(
                            text = it,
                            fontSize = 15.sp,
                            lineHeight = 20.sp, color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
