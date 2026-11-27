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

    private var loadedTaskId: String? = null

    fun loadTask(taskId: String?) {

        if (taskId == null) return

        if (loadedTaskId == taskId) return

        loadedTaskId = taskId

        viewModelScope.launch {

            val task = repository.getTask(taskId)
                ?: return@launch

            _uiState.value = _uiState.value.copy(
                title = task.title,
                taskType = task.type,
                reminderTime = task.reminderTime
            )
        }
    }

    fun updateTitle(value: String) {
        _uiState.value = _uiState.value.copy(
            title = value
        )
    }

    fun updateTaskType(type: TaskType) {
        _uiState.value = _uiState.value.copy(
            taskType = type
        )
    }

    fun updateReminderTime(time: Long) {
        _uiState.value = _uiState.value.copy(
            reminderTime = time
        )
    }

    fun saveTask(
        taskId: String?,
        onSaved: () -> Unit
    ) {

        viewModelScope.launch {

            if (taskId == null) {

                repository.addTask(
                    Task(
                        userId = BackendService.currentUserId() ?: "",
                        title = uiState.value.title,
                        type = uiState.value.taskType,
                        reminderTime = uiState.value.reminderTime
                    )
                )

            } else {

                repository.updateTask(
                    Task(
                        id = taskId,
                        userId = BackendService.currentUserId() ?: "",
                        title = uiState.value.title,
                        type = uiState.value.taskType,
                        reminderTime = uiState.value.reminderTime
                    )
                )
            }

            onSaved()
        }
    }

    fun deleteTask(
        taskId: String?,
        onDeleted: () -> Unit
    ) {

        val id = taskId ?: return

        viewModelScope.launch {

            repository.deleteTask(id)

            onDeleted()
        }
    }
}