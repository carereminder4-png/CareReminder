package com.carereminder.app.presentation.home

import com.carereminder.app.domain.model.Task

data class HomeUiState(
    val isLoading: Boolean = true,
    val tasks: List<Task> = emptyList()
)