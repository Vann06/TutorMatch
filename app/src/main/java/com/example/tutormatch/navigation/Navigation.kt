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
import com.example.tutormatch.ui.estudiante.Perfil.View.PerfilEstudianteScreen
import com.example.tutormatch.ui.estudiante.SolicitudTutoria.view.SolicitudTutoria
import com.example.tutormatch.ui.tutor.MisTutorias.View.MisTutoriasScreen
import com.example.tutormatch.ui.tutor.crearTutoria.View.CreacionTutoria
import com.example.tutormatch.ui.tutor.perfil.PerfilTutorScreen


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
            Bienvenida(navController = navController)
        }
        composable(NavigationState.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(NavigationState.SignUp.route) {
            SignUpScreen(navController = navController)
        }

        // ESTUDIANTE
        composable(NavigationState.Main_Es.route) {
            MainEstudiante(navController = navController)
        }
        composable(NavigationState.MyTutors.route) {
            MyTutorsScreen(navController = navController)
        }
        composable(NavigationState.SolicitudTutoria.route) {
            SolicitudTutoria(navHostController = navController, tutor = Tutor1())
        }
        composable(NavigationState.Perfil_Es.route) {
            PerfilEstudianteScreen(navController = navController)
        }

        // TUTOR
        composable(NavigationState.MisTutorias.route) {
            // Aquí debes definir la pantalla para MisTutorias
            MisTutoriasScreen(navController = navController)
        }
        composable(NavigationState.CrearTutoria.route) {
            CreacionTutoria(navController = navController)
        }
        composable(NavigationState.PerfilTutor.route) {
            // Aquí debes definir la pantalla para el Perfil del Tutor
            PerfilTutorScreen(navController = navController)
        }
    }
}
