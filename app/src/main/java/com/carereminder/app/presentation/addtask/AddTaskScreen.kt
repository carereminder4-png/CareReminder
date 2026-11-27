package com.carereminder.app.presentation.addtask

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddTaskScreen(
    taskId: String?,
    onTaskSaved: () -> Unit,
    onTaskDeleted: () -> Unit
) {

    val viewModel: AddTaskViewModel = viewModel()

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    val state by viewModel.uiState.collectAsState()

    AddTaskContent(
        state = state,
        isEditMode = taskId != null,
        onTitleChange = viewModel::updateTitle,
        onTaskTypeChange = viewModel::updateTaskType,
        onReminderTimeChange = viewModel::updateReminderTime,
        onSave = {
            viewModel.saveTask(
                taskId = taskId,
                onSaved = onTaskSaved
            )
        },
        onDelete = {
            viewModel.deleteTask(
                taskId = taskId,
                onDeleted = onTaskDeleted
            )
        }
    )
}