package com.example.tutormatch.ui.tutor.Estudiante_Tu.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tutormatch.ui.tutor.Estudiante_Tu.ViewModel.EstudianteTuViewModel

@Composable
fun EstudianteTuScreen(tutoriaId: String, navController: NavHostController){

    val viewModel: EstudianteTuViewModel = viewModel()
    LaunchedEffect(tutoriaId) {
        viewModel.cargarDetalleTutoria(tutoriaId)
    }
    val detalle by viewModel.tutoriaConDetalles.collectAsState()

    detalle?.let {solicitud->
        EstudianteTuContenido(solicitud,navController, viewModel)
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }    }
}
