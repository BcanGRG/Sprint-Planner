package com.bcan.sprintplanner.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SelectionCard(
    modifier: Modifier = Modifier,
    fieldName: String? = null,
    value: String?,
    placeHolder: String = "",
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    isError: Boolean = false,
    errorText: String? = null
) {
    Column(modifier = modifier) {
        fieldName?.let {
            Text(
                text = it,
                fontSize = 16.sp, letterSpacing = (0.3).sp,
                color = if (enabled) Color.Black else Color(0xFFC1C1C1),
            )
            Spacer(modifier = Modifier.height(7.dp))
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = enabled
                ) {
                    onClick()
                },
            border = BorderStroke(width = 1.dp, color = Color(0xFFC1C1C1)),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!value.isNullOrBlank()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = value,
                        fontSize = 15.sp, color = Color.Black
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .offset(x = 1.dp, y = (-1).dp),
                        text = placeHolder, letterSpacing = (0.3).sp,
                        fontSize = 15.sp, color = if (enabled) Color.Gray else Color(0xFFC1C1C1)
                    )
                }

                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .size(18.dp),
                    tint = if (enabled) Black else Color(0xFFC1C1C1)
                )
            }
        }
        if (isError) {
            errorText?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it,
                    fontSize = 12.sp,
                    lineHeight = 15.sp,
                    color = Red
                )
            }
        }
    }
}