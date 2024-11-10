package com.example.tutormatch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.ui.general.Login.View.LoginScreen
import com.example.tutormatch.ui.general.SignUp.View.SignUpScreen
import com.example.tutormatch.ui.general.bienvenida.View.Bienvenida
import com.example.tutormatch.ui.estudiante.Main.View.MainEstudiante
import com.example.tutormatch.ui.estudiante.Main.ViewModel.MainEstudianteViewModel
import com.example.tutormatch.ui.estudiante.MyTutors.View.MyTutorsScreen
import com.example.tutormatch.ui.estudiante.Perfil.View.PerfilEstudianteScreen
import com.example.tutormatch.ui.estudiante.SolicitudTutoria.view.SolicitudTutoria
import com.example.tutormatch.ui.estudiante.Tutor_Es.View.PerfilTutorEstudianteScreen
import com.example.tutormatch.ui.tutor.Estudiante_Tu.View.EstudianteTuScreen
import com.example.tutormatch.ui.tutor.MisTutorias.View.MisTutoriasScreen
import com.example.tutormatch.ui.tutor.crearTutoria.View.CreacionTutoria
import com.example.tutormatch.ui.tutor.perfil.PerfilTutorScreen

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier, viewModel: MainEstudianteViewModel) {
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
            MainEstudiante(navController = navController, viewModel = viewModel)
        }
        composable(NavigationState.MyTutors.route) {
            MyTutorsScreen(navController = navController)
        }
        composable(
            route = NavigationState.SolicitudTutoria.route,
            arguments = listOf(
                navArgument("tutorId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getString("tutorId")
            if (tutorId != null) {
                // Supongamos que tienes un método en el viewModel para obtener el tutor por ID
                val tutor = viewModel.getTutorById(tutorId)
                if (tutor != null) {
                    SolicitudTutoria(navController = navController, tutorId = tutorId)
                } else {
                    // Manejo de error si no se encuentra el tutor
                    navController.popBackStack()
                }
            } else {
                // Manejo de error si el tutorId es nulo
                navController.popBackStack()
            }
        }
        composable(NavigationState.Perfil_Es.route) {
            PerfilEstudianteScreen(navController = navController)
        }

        composable(
            route = NavigationState.PerfilTutorEstudiante.route,
            arguments = listOf(navArgument("tutorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tutorId = backStackEntry.arguments?.getString("tutorId")
            if (tutorId != null) {
                PerfilTutorEstudianteScreen(tutorId = tutorId, navController = navController)
            } else {
                // Manejo de error si tutorId es nulo
                navController.popBackStack()
            }
        }

        // TUTOR
        composable(NavigationState.MisTutorias.route) {
            MisTutoriasScreen(navController = navController)
        }
        composable(NavigationState.CrearTutoria.route) {
            CreacionTutoria(navController = navController)
        }
        composable(NavigationState.PerfilTutor.route) {
            PerfilTutorScreen(navController = navController)
        }
        // Agregar la nueva ruta
        composable(
            route = NavigationState.Estudiante_Tu.route,
            arguments = listOf(navArgument("tutoriaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val tutoriaId = backStackEntry.arguments?.getString("tutoriaId")
            if (tutoriaId != null) {
                EstudianteTuScreen(tutoriaId = tutoriaId, navController = navController)
            } else {
                // Manejo de error si tutoriaId es nulo
                navController.popBackStack()
            }
        }

    }
}
