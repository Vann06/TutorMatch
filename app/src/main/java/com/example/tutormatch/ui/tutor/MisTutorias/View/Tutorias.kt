package com.example.tutormatch.ui.tutor.MisTutorias.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.tutormatch.ui.tutor.MisTutorias.ViewModel.MisTutoriasViewModel
import com.example.tutormatch.ui.tutor.solicitudes.ViewModel.SolicitudesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal

@Composable
fun MisTutoriasContent(navController: NavHostController, viewModel: MisTutoriasViewModel = viewModel()) {
    val misTutorias by viewModel.misTutorias.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.cargarMisTutorias()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (misTutorias.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(misTutorias) { tutoria ->
                when (tutoria) {
                    is Tutoria1 -> {
                        TutoriaCard(navController = navController, tutoria = tutoria)
                    }
                    is TutoriaGrupal -> {
                        TutoriaGrupalCard(navController = navController, tutoriaGrupal = tutoria)
                    }
                    else -> {
                        Text(
                            text = "Tipo de tutoría desconocido",
                            color = Color.Red,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
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

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.cargarSolicitudes()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (solicitudes.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(solicitudes) { solicitud ->
                TutoriaCard(
                    navController = navController,
                    tutoria = solicitud
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
