package com.example.tutormatch.ui.tutor.crearTutoria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.ui.tutor.crearTutoria.Repository.CrearTutoriaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CrearTutoriaViewModel(
    private val repository: CrearTutoriaRepository = CrearTutoriaRepository()
) : ViewModel() {

    private val _materiasDisponibles = MutableStateFlow<List<Materia>>(emptyList())
    val materiasDisponibles: StateFlow<List<Materia>> = _materiasDisponibles

    private val _tiposDeTutoria = MutableStateFlow<List<String>>(listOf("Presencial", "Virtual"))
    val tiposDeTutoria: StateFlow<List<String>> = _tiposDeTutoria

    private val _estadoCreacion = MutableStateFlow<Result<String>?>(null)
    val estadoCreacion: StateFlow<Result<String>?> = _estadoCreacion

    init {
        cargarMateriasDisponibles()
    }

    private fun cargarMateriasDisponibles() {
        viewModelScope.launch {
            val materias = repository.obtenerMaterias()
            _materiasDisponibles.value = materias
        }
    }

    fun crearTutoria(
        materia: Materia?,
        tipoTutoria: String?,
        fecha: String,
        hora: String,
        descripcion: String
    ) {
        if (materia == null || tipoTutoria == null || fecha.isEmpty() || hora.isEmpty()) {
            _estadoCreacion.value = Result.failure(Exception("Todos los campos son obligatorios"))
            return
        }

        val nuevaTutoria = Tutoria1(
            id = "",
            tutorId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
            materiaId = materia.id,
            fecha = fecha,
            hora = hora,
            modalidad = tipoTutoria,
            mensaje = descripcion,
            estado = "Disponible"
        )

        viewModelScope.launch {
            val resultado = repository.crearTutoria(nuevaTutoria)
            _estadoCreacion.value = resultado
        }
    }

    // Reiniciar el estado después de manejarlo
    fun resetEstadoCreacion() {
        _estadoCreacion.value = null
    }
}
