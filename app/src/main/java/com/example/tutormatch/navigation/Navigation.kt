package com.example.tutormatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutormatch.ui.general.Login.View.LoginScreen
import com.example.tutormatch.ui.general.SignUp.View.SignUpScreen
import com.example.tutormatch.ui.general.bienvenida.View.Bienvenida
import com.example.tutormatch.ui.estudiante.Main.View.MainEstudiante


class Navigation {
}
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationState.Bienvenida.route,
        modifier = modifier
    ) {
        // GENERAL
        composable(NavigationState.Bienvenida.route) {
            // Pantalla de Bienvenida
            Bienvenida(navController = navController)
        }
        composable(NavigationState.Login.route) {
            // Pantalla de Login
            LoginScreen(navController = navController)
        }
        composable(NavigationState.SignUp.route) {
            // Pantalla de Sign Up
            SignUpScreen(navController = navController)
        }

        // ESTUDIANTE
        composable(NavigationState.Main_Es.route){
            //Pantalla Main
            MainEstudiante(navController = navController)
        }
    }
}