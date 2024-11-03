package com.example.tutormatch.ui.tutor.Perfil.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.ui.tutor.MisTutorias.Repository.PerfilTutorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilTutorViewModel(
    private val repository: PerfilTutorRepository = PerfilTutorRepository()
) : ViewModel() {

    private val _tutor = MutableStateFlow<Tutor1?>(null)
    val tutor: StateFlow<Tutor1?> = _tutor

    init {
        cargarDatosTutor()
    }

    private fun cargarDatosTutor() {
        viewModelScope.launch {
            val tutorActual = repository.obtenerTutorActual()
            _tutor.value = tutorActual
        }
    }

    fun actualizarTutor(tutor: Tutor1) {
        viewModelScope.launch {
            repository.actualizarTutor(tutor)
            _tutor.value = tutor
        }
    }
}
