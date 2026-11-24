package com.carereminder.app.data.repository

import com.carereminder.app.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(taskId: String)

    suspend fun getTask(taskId: String): Task?

    fun observeTasks(userId: String): Flow<List<Task>>
}