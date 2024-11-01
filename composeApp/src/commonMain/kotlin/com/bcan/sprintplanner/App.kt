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
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import sprintplanner.composeapp.generated.resources.Res
import sprintplanner.composeapp.generated.resources.app_title

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
            Scaffold {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(Res.string.app_title),
                            style = MaterialTheme.typography.h3,
                            color = secondaryLight, textAlign = TextAlign.Center
                        )
                        Navigator(HomeScreen())
                    }
                }
            }
        }
    }
}