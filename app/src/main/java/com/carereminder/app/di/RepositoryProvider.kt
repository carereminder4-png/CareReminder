package com.carereminder.app.di

import com.carereminder.app.data.repository.SupabaseTaskRepository
import com.carereminder.app.data.repository.TaskRepository

object RepositoryProvider {

    val taskRepository: TaskRepository by lazy {
        SupabaseTaskRepository()
    }
}