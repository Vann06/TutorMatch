package com.example.tutormatch.ui.tutor.crearTutoria.viewmodel

import androidx.compose.runtime.mutableStateOf
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

    private val _selectedMateria = MutableStateFlow<Materia?>(null)
    val selectedMateria: StateFlow<Materia?> = _selectedMateria

    private val _tiposDeTutoria = MutableStateFlow<List<String>>(listOf("Presencial", "Virtual"))
    val tiposDeTutoria: StateFlow<List<String>> = _tiposDeTutoria

    private val _estadoCreacion = MutableStateFlow<Result<String>?>(null)
    val estadoCreacion: StateFlow<Result<String>?> = _estadoCreacion

    private val _materiasTutor = MutableStateFlow<List<Materia>>(emptyList())
    val materiasTutor: StateFlow<List<Materia>> = _materiasTutor

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _selectedTime = MutableStateFlow("")
    val selectedTime: StateFlow<String> = _selectedTime

    var materiaDropdownExpanded = mutableStateOf(false)
        private set

    fun toggleMateriaDropdown() {
        materiaDropdownExpanded.value = !materiaDropdownExpanded.value
    }

    fun setSelectedMateria(materia: Materia?) {
        _selectedMateria.value = materia
        toggleMateriaDropdown()
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun setSelectedTime(time: String) {
        _selectedTime.value = time
    }

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
        descripcion: String,
        esGrupal: Boolean
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
            esGrupal = esGrupal
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
