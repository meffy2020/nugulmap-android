package com.example.neogulmap.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neogulmap.presentation.ui.screens.HomeScreen
import com.example.neogulmap.presentation.ui.screens.LoginScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Login.route) {
            LoginScreen()
        }
    }
}
