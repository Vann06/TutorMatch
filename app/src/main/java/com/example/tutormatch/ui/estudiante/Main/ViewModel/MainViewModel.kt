package com.example.tutormatch.ui.estudiante.Main.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.Materias
import com.example.tutormatch.estructuras.Tutor1
import com.example.tutormatch.ui.estudiante.Main.Repository.EstudianteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainEstudianteViewModel(private val repository: EstudianteRepository) : ViewModel() {

    private val _materias = MutableStateFlow<MutableList<Materias>>(mutableListOf())
    val materias: StateFlow<MutableList<Materias>> = _materias

    private val _tutors = MutableStateFlow<MutableList<Tutor1>>(mutableListOf())
    val tutors: StateFlow<MutableList<Tutor1>> = _tutors

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null
            try {
                _materias.value = repository.getMaterias()
                _tutors.value = repository.getTutors()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar datos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
