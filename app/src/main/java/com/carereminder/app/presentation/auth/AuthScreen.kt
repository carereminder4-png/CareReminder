package com.carereminder.app.presentation.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel(),
    onAuthSuccess: () -> Unit
) {

    val authState by viewModel.uiState
        .collectAsStateWithLifecycle()

    AuthContent(
        state = authState,

        onEmailChange = viewModel::onEmailChanged,

        onPasswordChange = viewModel::onPasswordChanged,

        onSubmit = {
            performAuthentication(
                viewModel = viewModel,
                onAuthSuccess = onAuthSuccess
            )
        },

        onToggleMode = viewModel::toggleAuthMode,

        onErrorShown = viewModel::clearErrorMessage
    )
}

private fun performAuthentication(
    viewModel: AuthViewModel,
    onAuthSuccess: () -> Unit
) {
    viewModel.submitAuth(onAuthSuccess)
}