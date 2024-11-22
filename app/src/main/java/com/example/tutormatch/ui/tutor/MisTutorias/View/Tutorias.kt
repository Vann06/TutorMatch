package com.example.tutormatch.ui.tutor.MisTutorias.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.tutormatch.ui.tutor.MisTutorias.ViewModel.MisTutoriasViewModel
import com.example.tutormatch.ui.tutor.solicitudes.ViewModel.SolicitudesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun MisTutoriasContent(navController: NavHostController, viewModel: MisTutoriasViewModel = viewModel()) {
    val misTutoriasIndividuales by viewModel.misTutoriasIndividuales.collectAsState()
    val misTutoriasGrupales by viewModel.misTutoriasGrupales.collectAsState()

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Text(text = "Tutorías Individuales", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(12.dp))
        }
        if (misTutoriasIndividuales.isNotEmpty()) {
            items(misTutoriasIndividuales) { tutoria ->
                TutoriaCard(navController = navController, tutoria = tutoria)
            }
        } else {
            item {
                Text(text = "No tienes tutorías individuales.", modifier = Modifier.padding(8.dp))
            }
        }

        item {
            Text(text = "Tutorías Grupales", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(12.dp))
        }
        if (misTutoriasGrupales.isNotEmpty()) {
            items(misTutoriasGrupales) { tutoriaGrupal ->
                TutoriaGrupalCard(navController = navController, tutoriaGrupal = tutoriaGrupal)
            }
        } else {
            item {
                Text(text = "No tienes tutorías grupales.", modifier = Modifier.padding(8.dp))
            }
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
