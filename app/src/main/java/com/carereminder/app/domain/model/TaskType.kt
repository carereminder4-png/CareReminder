package com.carereminder.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class TaskType{
    MEDICINE,
    MEAL,
    EXERCISE,
    OTHER
}