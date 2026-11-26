package com.carereminder.app.presentation.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carereminder.app.data.remote.BackendService
import com.carereminder.app.di.RepositoryProvider
import com.carereminder.app.domain.model.Task
import com.carereminder.app.domain.model.TaskType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddTaskViewModel : ViewModel() {

    private val repository = RepositoryProvider.taskRepository

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    fun updateTitle(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
    }

    fun updateTaskType(type: TaskType) {
        _uiState.value = _uiState.value.copy(taskType = type)
    }

    fun updateReminderTime(time: Long) {
        _uiState.value = _uiState.value.copy(reminderTime = time)
    }

    fun saveTask(onSaved: () -> Unit) {

        val state = _uiState.value

        viewModelScope.launch {

            repository.addTask(
                Task(
                    userId = BackendService.currentUserId() ?: "",
                    title = state.title,
                    type = state.taskType,
                    reminderTime = state.reminderTime
                )
            )

            onSaved()
        }
    }
}