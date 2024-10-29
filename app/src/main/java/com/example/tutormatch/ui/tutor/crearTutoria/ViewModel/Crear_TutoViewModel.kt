package com.example.tutormatch.ui.tutor.crearTutoria.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tutormatch.estructuras.Materia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CrearTutoriaViewModel : ViewModel() {

    // Lista de materias disponibles para la tutoría
    private val _materiasDisponibles = MutableStateFlow<List<Materia>>(emptyList())
    val materiasDisponibles: StateFlow<List<Materia>> get() = _materiasDisponibles

    // Lista de tipos de tutoría (nivel)
    private val _tiposDeTutoria = MutableStateFlow<List<String>>(emptyList())
    val tiposDeTutoria: StateFlow<List<String>> get() = _tiposDeTutoria

    init {
        cargarDatosSimulados()
    }

    // Cargar datos simulados de materias y tipos de tutoría
    private fun cargarDatosSimulados() {
        val materia1 = Materia(
            id = "mat1",
            nombre = "Matemáticas"
        )
        val materia2 = Materia(
            id = "mat2",
            nombre = "Física"
        )
        val materia3 = Materia(
            id = "mat3",
            nombre = "Química"
        )

        // Lista simulada de materias
        val listaMaterias = listOf(materia1, materia2, materia3)
        _materiasDisponibles.value = listaMaterias

        // Lista simulada de tipos de tutoría (nivel)
        val listaTiposDeTutoria = listOf("Desde 0", "Intermedio", "Avanzado")
        _tiposDeTutoria.value = listaTiposDeTutoria
    }
}
