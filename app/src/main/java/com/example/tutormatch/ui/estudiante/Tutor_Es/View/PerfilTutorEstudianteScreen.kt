package com.example.tutormatch.ui.estudiante.Tutor_Es.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tutormatch.ui.estudiante.Tutor_Es.ViewModel.PerfilTutorEstudianteViewModel
import com.example.tutormatch.ui.estudiante.Tutor_Es.ViewModel.PerfilTutorEstudianteViewModelFactory

@Composable
fun PerfilTutorEstudianteScreen(
    tutorId: String,
    navController: NavHostController
) {
    val viewModel: PerfilTutorEstudianteViewModel = viewModel(
        factory = PerfilTutorEstudianteViewModelFactory(tutorId)
    )

    val tutorState by viewModel.tutor.collectAsState()

    if (tutorState != null) {
        PerfilTutorEstudiante(tutor = tutorState!!, navController = navController, viewModel = viewModel)
    } else {
        // Mostrar un indicador de carga o un mensaje de error
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}