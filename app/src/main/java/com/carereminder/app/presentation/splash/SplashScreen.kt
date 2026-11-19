package com.carereminder.app.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToAuth: () -> Unit
) {

    val destination = viewModel.destination
        .collectAsStateWithLifecycle()
        .value

    LaunchedEffect(destination) {

        if (destination == null) return@LaunchedEffect

        if (destination == SplashDestination.Dashboard) {
            onNavigateToHome()
            return@LaunchedEffect
        }

        onNavigateToAuth()
    }

    SplashContent()
}