package com.example.tutormatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.ui.general.Login.View.LoginScreen
import com.example.tutormatch.ui.general.SignUp.View.SignUpScreen
import com.example.tutormatch.ui.general.bienvenida.View.Bienvenida
import com.example.tutormatch.ui.estudiante.Main.View.MainEstudiante
import com.example.tutormatch.ui.estudiante.MyTutors.View.MyTutorsScreen
import com.example.tutormatch.ui.estudiante.SolicitudTutoria.view.SolicitudTutoria
import com.example.tutormatch.ui.tutor.MisTutorias.View.MisTutorias
import com.example.tutormatch.ui.tutor.crearTutoria.View.CreacionTutoria


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

        composable(NavigationState.MyTutors.route){
            // 
            MyTutorsScreen(navController = navController)

        }
        
        composable(NavigationState.SolicitudTutoria.route){
            //Pantalla solicitud de tutoria

            SolicitudTutoria(navHostController = navController, tutor = Tutor1())
        }

        // TUTOR
        composable(NavigationState.MisTutorias.route){
            //Pantalla Mis Tutorias
            MisTutorias(navController = navController, infoEstudiante = Estudiante1())
        }
        composable(NavigationState.CrearTutoria.route){
            CreacionTutoria(navHostController = navController)
        }
        


    }
}