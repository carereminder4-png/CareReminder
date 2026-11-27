package com.carereminder.app.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    onAddTaskClick: () -> Unit,
    onEditTask: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshTasks()
    }

    HomeContent(
        state = state,
        onAddTaskClick = onAddTaskClick,
        onToggleComplete = viewModel::toggleTaskCompletion,
        onEditTask = onEditTask
    )
}