package com.example.tutormatch.ui.tutor.MisTutorias.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.tutormatch.ui.tutor.MisTutorias.ViewModel.MisTutoriasViewModel
import com.example.tutormatch.ui.tutor.solicitudes.ViewModel.SolicitudesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MisTutoriasContent(navController: NavHostController, viewModel: MisTutoriasViewModel = viewModel()) {
    val misTutorias by viewModel.misTutorias.collectAsState()

    if (misTutorias.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(misTutorias) { tutoria ->
                TutoriaCard(navController = navController, tutoria = tutoria)
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No tienes tutorías pendientes")
        }
    }
}

@Composable
fun SolicitudesContent(navController: NavHostController, viewModel: SolicitudesViewModel = viewModel()) {
    val solicitudes by viewModel.solicitudes.collectAsState()

    if (solicitudes.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(solicitudes) { solicitud ->
                TutoriaCard(
                    navController = navController,
                    tutoria = solicitud,
                    esSolicitud = true,
                    onAceptarSolicitud = { tutoriaAceptada ->
                        // Lógica para aceptar la solicitud
                        viewModel.aceptarSolicitud(tutoriaAceptada)
                    },
                    onRechazarSolicitud = { tutoriaRechazada ->
                        // Lógica para rechazar la solicitud
                        viewModel.rechazarSolicitud(tutoriaRechazada)
                    }
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No hay solicitudes de tutoría")
        }
    }
}
