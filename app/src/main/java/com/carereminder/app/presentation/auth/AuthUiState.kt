package com.carereminder.app.presentation.auth

import android.util.Patterns

data class AuthUiState(

    val userEmail: String = "",

    val userPassword: String = "",

    val authMode: AuthMode = AuthMode.Login,

    val isLoading: Boolean = false,

    val errorMessage: String? = null
) {

    val emailValid: Boolean
        get() = userEmail.isValidEmail()

    val passwordValid: Boolean
        get() = userPassword.length >= MIN_PASSWORD_LENGTH

    val formValid: Boolean
        get() = emailValid && passwordValid

    val canSubmit: Boolean
        get() = formValid && !isLoading
}

enum class AuthMode {
    Login,
    Register
}

private fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS
        .matcher(this)
        .matches()
}

private const val MIN_PASSWORD_LENGTH = 6