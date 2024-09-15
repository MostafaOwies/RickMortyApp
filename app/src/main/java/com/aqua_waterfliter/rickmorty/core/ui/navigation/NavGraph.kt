package com.aqua_waterfliter.rickmorty.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aqua_waterfliter.rickmorty.homeScreen.presentation.HomeScreen
import com.aqua_waterfliter.rickmorty.loginScreen.LoginScreen
import com.aqua_waterfliter.rickmorty.loginScreen.RegisterScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("home") },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("home") {
            HomeScreen() // Replace with your home screen composable
        }
    }
}

