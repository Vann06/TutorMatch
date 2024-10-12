package com.example.tutormatch.ui.tutor.Estudiante_Tu.View

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutormatch.ui.tutor.Estudiante_Tu.ViewModel.Estudiante_Tu_ViewModel

@Composable
fun EstudianteTuScreen(){

    val viewModel: Estudiante_Tu_ViewModel = viewModel()
    val detalle by viewModel.tutoriaConDetalles.collectAsState()

    detalle?.let {solicitud->
        EstudianteTuContenido(solicitud)
    } ?: run {
        Text(text = "Cargando...", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEstudianteDetailScreen() {
    EstudianteTuScreen()
}