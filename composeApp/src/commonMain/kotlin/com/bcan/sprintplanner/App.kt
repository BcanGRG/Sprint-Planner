package com.bcan.sprintplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bcan.sprintplanner.themes.backgroundLight
import com.bcan.sprintplanner.themes.errorLight
import com.bcan.sprintplanner.themes.onBackgroundLight
import com.bcan.sprintplanner.themes.onErrorLight
import com.bcan.sprintplanner.themes.onPrimaryLight
import com.bcan.sprintplanner.themes.onSecondaryLight
import com.bcan.sprintplanner.themes.onSurfaceLight
import com.bcan.sprintplanner.themes.primaryContainerLight
import com.bcan.sprintplanner.themes.primaryLight
import com.bcan.sprintplanner.themes.secondaryContainerLight
import com.bcan.sprintplanner.themes.secondaryLight
import com.bcan.sprintplanner.themes.surfaceLight
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        colors = Colors(
            primary = primaryLight,
            primaryVariant = primaryContainerLight,
            secondary = secondaryLight,
            secondaryVariant = secondaryContainerLight,
            background = backgroundLight,
            surface = surfaceLight,
            error = errorLight,
            onPrimary = onPrimaryLight,
            onSecondary = onSecondaryLight,
            onBackground = onBackgroundLight,
            onSurface = onSurfaceLight,
            onError = onErrorLight,
            isLight = false,
        )
    ) {
        Scaffold {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Sprint Planner",
                        style = MaterialTheme.typography.h2,
                        color = secondaryLight, textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}