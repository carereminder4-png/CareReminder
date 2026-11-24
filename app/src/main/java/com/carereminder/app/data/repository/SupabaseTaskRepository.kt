package com.carereminder.app.data.repository

import com.carereminder.app.data.remote.BackendService
import com.carereminder.app.domain.model.Task
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SupabaseTaskRepository : TaskRepository {

    private val client = BackendService.supabaseInstance

    override suspend fun addTask(task: Task) {
        client.from("tasks").insert(task)
    }

    override suspend fun updateTask(task: Task) {
        client.from("tasks")
            .update(task) {
                filter {
                    eq("id", task.id)
                }
            }
    }

    override suspend fun deleteTask(taskId: String) {
        client.from("tasks")
            .delete {
                filter {
                    eq("id", taskId)
                }
            }
    }

    override suspend fun getTask(taskId: String): Task? {
        return client.from("tasks")
            .select {
                filter {
                    eq("id", taskId)
                }
            }
            .decodeSingleOrNull<Task>()
    }

    override fun observeTasks(userId: String): Flow<List<Task>> = flow {
        val tasks = client.from("tasks")
            .select {
                filter {
                    eq("user_id", userId)
                }
            }
            .decodeList<Task>()

        emit(tasks)
    }
}