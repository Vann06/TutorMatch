package com.example.tutormatch.ui.estudiante.Perfil.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.ui.estudiante.Perfil.Repository.PerfilEstudianteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilEstudianteViewModel(
    private val repository: PerfilEstudianteRepository = PerfilEstudianteRepository()
) : ViewModel() {

    private val _estudiante = MutableStateFlow<Estudiante1?>(null)
    val estudiante: StateFlow<Estudiante1?> = _estudiante

    init {
        cargarDatosEstudiante()
    }

    private fun cargarDatosEstudiante() {
        viewModelScope.launch {
            val estudiante = repository.obtenerEstudianteActual()
            _estudiante.value = estudiante
        }
    }

    fun actualizarEstudiante(estudiante: Estudiante1) {
        viewModelScope.launch {
            repository.actualizarEstudiante(estudiante)
            _estudiante.value = estudiante
        }
    }
}
