package com.example.tutormatch.ui.tutor.MisTutorias.ViewModel

import androidx.lifecycle.ViewModel
import com.example.tutormatch.estructuras.Materia
import com.example.tutormatch.estructuras.Tutoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MisTutoriasViewModel : ViewModel() {

    // Lista de tutorías del estudiante
    private val _misTutorias = MutableStateFlow<List<Tutoria>>(emptyList())
    val misTutorias: StateFlow<List<Tutoria>> get() = _misTutorias

    init {
        cargarTutoriasSimuladas()
    }

    // Cargar tutorías simuladas del estudiante
    private fun cargarTutoriasSimuladas() {
        val materia1 = Materia(
            id = "mat1",
            nombre = "Física 1"
        )
        val materia2 = Materia(
            id = "mat2",
            nombre = "Matemáticas"
        )

        val tutor1 = com.example.tutormatch.estructuras.Tutor(
            id = "tutor1",
            nombre = "Ricardo Godinez",
            usuario = "ricardo123",
            contraseña = "password",
            myStudents = mutableListOf(),
            fotoPerfil = null, // Puedes añadir una URL o referencia de drawable aquí
            materias = mutableListOf(materia1),
            descripcion = "Profesor con experiencia en Física.",
            modalidad = "Virtual"
        )

        val tutor2 = com.example.tutormatch.estructuras.Tutor(
            id = "tutor2",
            nombre = "Diego López",
            usuario = "diego456",
            contraseña = "password123",
            myStudents = mutableListOf(),
            fotoPerfil = null, // Puedes añadir una URL o referencia de drawable aquí
            materias = mutableListOf(materia2),
            descripcion = "Experto en Matemáticas.",
            modalidad = "Presencial"
        )

        val tutoria1 = Tutoria(
            id = "tut1",
            fecha = "2024-02-10",
            hora = "17:35",
            modalidad = "Virtual",
            tutor = tutor1,
            materia = materia1
        )

        val tutoria2 = Tutoria(
            id = "tut2",
            fecha = "2024-03-10",
            hora = "14:00",
            modalidad = "Presencial",
            tutor = tutor2,
            materia = materia2
        )

        val listaTutorias = listOf(tutoria1, tutoria2)
        _misTutorias.value = listaTutorias
    }
}
