package com.carereminder.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carereminder.app.data.BackendService
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _destination =
        MutableStateFlow<SplashDestination?>(null)

    val destination: StateFlow<SplashDestination?> =
        _destination

    init {
        monitorUserSession()
    }

    private fun monitorUserSession() {

        viewModelScope.launch {

            BackendService.supabaseInstance.auth.sessionStatus.collect { sessionState ->

                delay(SPLASH_SCREEN_DURATION)

                _destination.value = when (sessionState) {

                    is SessionStatus.Authenticated ->
                        SplashDestination.Dashboard

                    is SessionStatus.NotAuthenticated ->
                        SplashDestination.Login

                    else -> null
                }
            }
        }
    }

    companion object {
        private const val SPLASH_SCREEN_DURATION = 1000L
    }
}

enum class SplashDestination {
    Dashboard,
    Login
}