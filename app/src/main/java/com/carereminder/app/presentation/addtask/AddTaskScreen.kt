package com.carereminder.app.presentation.addtask

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddTaskScreen(
    onTaskSaved: () -> Unit
) {

    val viewModel: AddTaskViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    AddTaskContent(
        state = state,
        onTitleChange = viewModel::updateTitle,
        onTaskTypeChange = viewModel::updateTaskType,
        onSave = {
            viewModel.saveTask(
                onSaved = onTaskSaved
            )
        }
    )
}