package com.example.tutormatch.ui.estudiante.SolicitudTutoria

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SolicitudTutoriaViewModel(
    private val repository: SolicitudTutoriaRepository = SolicitudTutoriaRepository()
) : ViewModel() {

    // *** Variables de Estado ***
    private val _materias = MutableStateFlow<List<Materia>>(emptyList())
    val materias: StateFlow<List<Materia>> = _materias

    private val _selectedMateria = MutableStateFlow<Materia?>(null)
    val selectedMateria: StateFlow<Materia?> = _selectedMateria

    private val _selectedTipoTutoria = MutableStateFlow<String?>(null)
    val selectedTipoTutoria: StateFlow<String?> = _selectedTipoTutoria

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _selectedTime = MutableStateFlow("")
    val selectedTime: StateFlow<String> = _selectedTime

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _tutoriaCreationStatus = MutableStateFlow<Result<Unit>?>(null)
    val tutoriaCreationStatus: StateFlow<Result<Unit>?> = _tutoriaCreationStatus

    private val _materiaCreationStatus = MutableStateFlow<Result<Unit>?>(null)
    val materiaCreationStatus: StateFlow<Result<Unit>?> = _materiaCreationStatus

    var materiaDropdownExpanded by mutableStateOf(false)
        private set

    var tipoTutoriaDropdownExpanded by mutableStateOf(false)
        private set

    // *** Métodos de Configuración ***
    fun toggleMateriaDropdown() {
        materiaDropdownExpanded = !materiaDropdownExpanded
    }

    fun setSelectedMateria(materia: Materia?) {
        _selectedMateria.value = materia
        toggleMateriaDropdown()
    }

    fun toggleTipoTutoriaDropdown() {
        tipoTutoriaDropdownExpanded = !tipoTutoriaDropdownExpanded
    }

    fun setSelectedTipoTutoria(tipo: String) {
        _selectedTipoTutoria.value = tipo
        toggleTipoTutoriaDropdown()
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun setSelectedTime(time: String) {
        _selectedTime.value = time
    }

    fun setComment(comment: String) {
        _comment.value = comment
    }

    // *** Métodos para Crear y Obtener Tutorías ***
    fun createTutoria(estudianteId: String, tutorId: String) {
        val materiaId = selectedMateria.value?.id ?: return
        val tutoria = Tutoria1(
            id = System.currentTimeMillis().toString(), // ID único basado en el tiempo
            estudianteId = estudianteId,
            tutorId = tutorId,
            materiaId = materiaId,
            fecha = selectedDate.value,
            hora = selectedTime.value,
            modalidad = selectedTipoTutoria.value ?: "",
            mensaje = comment.value
        )

        viewModelScope.launch {
            _tutoriaCreationStatus.value = repository.createTutoria(tutoria)
        }
    }

    // *** Métodos para Materias ***
    fun fetchMaterias() {
        viewModelScope.launch {
            val result = repository.getMaterias()
            if (result.isSuccess) {
                _materias.value = result.getOrDefault(emptyList())
            } else {
                println("Error al obtener materias: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun addMateria(materia: Materia) {
        viewModelScope.launch {
            val result = repository.agregarMateria(materia)
            _materiaCreationStatus.value = result
            if (result.isSuccess) {
                fetchMaterias() // Actualiza la lista después de agregar
            } else {
                println("Error al agregar materia: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun getMateriaById(id: String): Materia? {
        return _materias.value.find { it.id == id }
    }
}
