package com.aqua_waterfliter.rickmorty.core.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aqua_waterfliter.rickmorty.homeScreen.presentation.HomeScreen
import com.aqua_waterfliter.rickmorty.loginScreen.LoginScreen
import com.aqua_waterfliter.rickmorty.loginScreen.RegisterScreen

@Composable
fun NavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    isLoggedIn: Boolean,
) {

    val context = LocalContext.current

    NavHost(
        navController, startDestination =
        if (isLoggedIn) "home" else "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate("home")
                },
                onNavigateToRegister = {
                    Toast.makeText(context, "Register", Toast.LENGTH_SHORT).show()
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate("login")

                },
                onNavigateToLogin = {
                    Toast.makeText(context, "Login", Toast.LENGTH_SHORT).show()
                    navController.navigate("login")
                }
            )
        }
        composable("home") {
            HomeScreen() // Replace with your home screen composable
        }
    }
}

