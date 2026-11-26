package com.carereminder.app.presentation.addtask

import com.carereminder.app.domain.model.TaskType

data class AddTaskUiState(
    val title: String = "",
    val taskType: TaskType = TaskType.MEDICINE,
    val reminderTime: Long = System.currentTimeMillis(),
    val isSaving: Boolean = false
)