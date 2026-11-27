package com.carereminder.app.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

@Serializable
object AuthRoute

@Serializable
object HomeRoute

@Serializable
data class AddTaskRoute(
    val taskId: String? = null
)