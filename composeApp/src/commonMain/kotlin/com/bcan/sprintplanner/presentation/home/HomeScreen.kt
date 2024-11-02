package com.bcan.sprintplanner.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.bcan.sprintplanner.presentation.sprint.SprintScreen
import com.bcan.sprintplanner.ui.SprintPlannerLoadingIndicator

class HomeScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        if (uiState.isLoading) SprintPlannerLoadingIndicator()


        Box(
            modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                modifier = Modifier.clickable {
                    if (viewModel.checkDocumentExists("101")) {
                        navigator.push(SprintScreen(sprintId = "Sprint 94"))
                    } else {
                        viewModel.createNewSprint("101")
                    }
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Sprint Icon",
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Create New Sprint",
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }

        if (uiState.sprints.isNullOrEmpty().not()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize().padding(top = 48.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                items(uiState.sprints!!) { sprint ->
                    Card(
                        modifier = Modifier.padding(16.dp).height(150.dp)
                            .clickable { navigator.push(SprintScreen(sprintId = "Sprint ${sprint.sprintId}")) },
                        shape = RoundedCornerShape(16.dp),
                        contentColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = 3.dp,
                        border = BorderStroke(2.dp, MaterialTheme.colors.secondary)
                    ) {
                        Box {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "Sprint ${sprint.sprintId}",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5, lineHeight = 0.sp
                            )
                        }
                    }
                }
            }
        }
    }
}