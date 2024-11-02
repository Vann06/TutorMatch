package com.example.tutormatch.ui.tutor.MisTutorias.ViewModel

import androidx.lifecycle.ViewModel
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MisTutoriasViewModel : ViewModel() {

    // Lista de tutorías del estudiante
    private val _misTutorias = MutableStateFlow<List<Tutoria1>>(emptyList())
    val misTutorias: StateFlow<List<Tutoria1>> get() = _misTutorias

    init {
        cargarTutoriasSimuladas()
    }

    // Cargar tutorías simuladas del estudiante
    private fun cargarTutoriasSimuladas() {
        // Definir las materias
        val materia1 = Materia(
            id = "mat1",
            nombre = "Física 1"
        )
        val materia2 = Materia(
            id = "mat2",
            nombre = "Matemáticas"
        )

        // Crear los tutores
        val tutor1 = Tutor1(
            id = "tutor1",
            nombre = "Ricardo Godinez",
            usuario = "ricardo123",
            fotoPerfilUrl = null.toString(), // URL o referencia de drawable
            email = "ricardo@example.com",
            descripcion = "Profesor con experiencia en Física.",
            modalidad = "Virtual",
            materiasIds = listOf(materia1.id),
            estudiantesIds = emptyList()
        )

        val tutor2 = Tutor1(
            id = "tutor2",
            nombre = "Diego López",
            usuario = "diego456",
            fotoPerfilUrl = null.toString(), // URL o referencia de drawable
            email = "diego@example.com",
            descripcion = "Experto en Matemáticas.",
            modalidad = "Presencial",
            materiasIds = listOf(materia2.id),
            estudiantesIds = emptyList()
        )

        // Crear las tutorías
        val tutoria1 = Tutoria1(
            id = "tut1",
            estudianteId = "est1",
            tutorId = tutor1.id,
            materiaId = materia1.id,
            fecha = "2024-02-10",
            hora = "17:35",
            modalidad = "Virtual",
            mensaje = "Repaso de temas de Física",
            estado = "Pendiente"
        )

        val tutoria2 = Tutoria1(
            id = "tut2",
            estudianteId = "est2",
            tutorId = tutor2.id,
            materiaId = materia2.id,
            fecha = "2024-03-10",
            hora = "14:00",
            modalidad = "Presencial",
            mensaje = "Preparación para el examen de Matemáticas",
            estado = "Pendiente"
        )

        // Asignar las tutorías simuladas
        _misTutorias.value = listOf(tutoria1, tutoria2)
    }
}
