package com.carereminder.app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.carereminder.app.presentation.auth.AuthScreen
import com.carereminder.app.presentation.home.HomeScreen
import com.carereminder.app.presentation.splash.SplashScreen
import com.carereminder.app.presentation.splash.SplashViewModel

@Composable
fun CareReminderNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashRoute
    ) {

        composable<SplashRoute> {

            val splashViewModel: SplashViewModel = viewModel()

            SplashScreen(
                viewModel = splashViewModel,

                onNavigateToHome = {

                    navController.navigate(HomeRoute) {

                        popUpTo<SplashRoute> {
                            inclusive = true
                        }
                    }
                },

                onNavigateToAuth = {

                    navController.navigate(AuthRoute) {

                        popUpTo<SplashRoute> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<AuthRoute> {

            AuthScreen(

                onAuthSuccess = {

                    navController.navigate(HomeRoute) {

                        popUpTo<AuthRoute> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<HomeRoute> {

            HomeScreen(

                onAddTaskClick = {
                    navController.navigate(AddTaskRoute)
                }
            )
        }
    }
}