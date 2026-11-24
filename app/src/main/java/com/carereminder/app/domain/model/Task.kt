package com.carereminder.app.domain.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Task(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val title: String = "",
    val type: TaskType = TaskType.OTHER,
    val reminderTime: Long = 0L,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)