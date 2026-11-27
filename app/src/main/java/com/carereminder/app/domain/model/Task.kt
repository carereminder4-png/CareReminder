package com.carereminder.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Task(

    val id: String = UUID.randomUUID().toString(),

    @SerialName("user_id")
    val userId: String = "",

    val title: String = "",

    val type: TaskType = TaskType.OTHER,

    @SerialName("reminder_time")
    val reminderTime: Long = 0L,

    val completed: Boolean = false,

    @SerialName("created_at")
    val createdAt: Long = System.currentTimeMillis()
)