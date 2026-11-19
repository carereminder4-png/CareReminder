package com.carereminder.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carereminder.app.data.BackendService
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(AuthUiState())

    val uiState: StateFlow<AuthUiState> =
        _uiState.asStateFlow()

    fun onEmailChanged(email: String) {

        _uiState.update {
            it.copy(userEmail = email)
        }
    }

    fun onPasswordChanged(password: String) {

        _uiState.update {
            it.copy(userPassword = password)
        }
    }

    fun toggleAuthMode() {

        _uiState.update { state ->

            state.copy(
                authMode =
                    if (state.authMode == AuthMode.Login)
                        AuthMode.Register
                    else
                        AuthMode.Login,

                errorMessage = null
            )
        }
    }

    fun submitAuth(
        onSuccess: () -> Unit
    ) {

        val currentState = _uiState.value

        if (!currentState.canSubmit) return

        viewModelScope.launch {

            updateLoadingState(true)

            try {

                when (currentState.authMode) {

                    AuthMode.Login -> {
                        loginUser(currentState)
                    }

                    AuthMode.Register -> {
                        registerUser(currentState)
                    }
                }

                onSuccess()

            } catch (e: Exception) {

                displayError(
                    e.message ?: FALLBACK_ERROR_MESSAGE
                )

            } finally {

                updateLoadingState(false)
            }
        }
    }

    private suspend fun loginUser(
        state: AuthUiState
    ) {

        BackendService.supabaseInstance.auth.signInWith(Email) {

            email = state.userEmail
            password = state.userPassword
        }
    }

    private suspend fun registerUser(
        state: AuthUiState
    ) {

        BackendService.supabaseInstance.auth.signUpWith(Email) {

            email = state.userEmail
            password = state.userPassword
        }
    }

    fun clearErrorMessage() {

        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    fun signOut(
        onComplete: () -> Unit
    ) {

        viewModelScope.launch {

            BackendService.supabaseInstance.auth.signOut()

            onComplete()
        }
    }

    private fun updateLoadingState(
        loading: Boolean
    ) {

        _uiState.update {
            it.copy(isLoading = loading)
        }
    }

    private fun displayError(
        message: String
    ) {

        _uiState.update {
            it.copy(errorMessage = message)
        }
    }

    companion object {

        private const val FALLBACK_ERROR_MESSAGE =
            "Something went wrong"
    }
}