package com.example.tutormatch.ui.tutor.Estudiante_Tu.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.Estudiante1
import com.example.tutormatch.estructuras.Materia
import com.example.tutormatch.estructuras.Tutoria1
import com.example.tutormatch.estructuras.TutoriaConDetalles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Estudiante_Tu_ViewModel(
) : ViewModel() {

    private val _tutoriaConDetalles = MutableStateFlow<TutoriaConDetalles?>(null)
    val tutoriaConDetalles: StateFlow<TutoriaConDetalles?> get() = _tutoriaConDetalles

    init {
        cargarDetalleTutoriaSimulada()
    }

    private fun cargarDetalleTutoriaSimulada() {
        val estudiante = Estudiante1(
            id = "est1",
            nombre = "Juan Pérez",
            fotoPerfilUrl = "" // Puedes usar una URL real o dejarla vacía
        )
        val materia = Materia(
            id = "mat1",
            nombre = "Matemáticas"
        )
        val tutoria = Tutoria1(
            id = "tut1",
            estudianteId = "est1",
            tutorId = "tutor1",
            materiaId = "mat1",
            fecha = "2024-10-15",
            hora = "10:00 AM",
            modalidad = "Virtual",
            mensaje = "Necesito ayuda con integrales.",
            estado = "pendiente"
        )

        val detalle = TutoriaConDetalles(tutoria, estudiante, materia)
        _tutoriaConDetalles.value = detalle
    }
}