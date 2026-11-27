package com.carereminder.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carereminder.app.data.remote.BackendService
import com.carereminder.app.di.RepositoryProvider
import com.carereminder.app.domain.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = RepositoryProvider.taskRepository

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refreshTasks()
    }

    fun refreshTasks() {

        val userId = BackendService.currentUserId() ?: return

        viewModelScope.launch {

            repository.observeTasks(userId).collect { tasks ->

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        tasks = tasks
                    )
                }
            }
        }
    }

    fun toggleTaskCompletion(task: Task) {

        viewModelScope.launch {

            repository.updateTask(
                task.copy(
                    completed = !task.completed
                )
            )

            refreshTasks()
        }
    }
}