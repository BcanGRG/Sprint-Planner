package com.bcan.sprintplanner.presentation.home

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<HomeViewModel>()

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        if(uiState.isLoading) CircularProgressIndicator()

        Text(uiState.sprints.toString())
        Text(uiState.tasks.toString())
    }
}