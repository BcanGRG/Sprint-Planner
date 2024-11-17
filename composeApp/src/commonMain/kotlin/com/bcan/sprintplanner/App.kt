package com.bcan.sprintplanner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.bcan.sprintplanner.di.provideFirebaseModule
import com.bcan.sprintplanner.di.provideRepositoryBindModules
import com.bcan.sprintplanner.di.provideViewModelModule
import com.bcan.sprintplanner.presentation.home.HomeScreen
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
import com.bcan.sprintplanner.ui.snackbar.ObserveAsEvents
import com.bcan.sprintplanner.ui.snackbar.SnackbarController
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

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
            isLight = true,
        )
    ) {
        KoinApplication(application = {
            modules(
                provideFirebaseModule,
                provideRepositoryBindModules,
                provideViewModelModule
            )
        }) {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action?.name,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) event.action?.action?.invoke()
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) {
                        Snackbar(
                            snackbarData = it,
                            contentColor = MaterialTheme.colors.onPrimary,
                            backgroundColor = MaterialTheme.colors.primary
                        )
                    }
                }
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigator(HomeScreen())
                }
            }
        }
    }
}