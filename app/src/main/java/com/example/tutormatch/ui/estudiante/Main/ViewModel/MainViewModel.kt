package com.example.tutormatch.ui.estudiante.Main.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.ui.estudiante.Main.Repository.EstudianteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainEstudianteViewModel(private val repository: EstudianteRepository) : ViewModel() {

    private val _materias = MutableStateFlow<MutableList<Materia>>(mutableListOf())
    val materias: StateFlow<MutableList<Materia>> = _materias

    private val _materiasMap = MutableStateFlow<Map<String, String>>(emptyMap())
    val materiasMap: StateFlow<Map<String, String>> = _materiasMap

    private val _tutors = MutableStateFlow<MutableList<Tutor1>>(mutableListOf())
    val tutors: StateFlow<MutableList<Tutor1>> = _tutors

    private val _materiaSeleccionada = MutableStateFlow<String?>(null)
    val materiaSeleccionada: StateFlow<String?> = _materiaSeleccionada

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadData()
    }

    fun setMateriaSeleccionada(materiaId: String?) {
        _materiaSeleccionada.value = materiaId
    }

    private fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null
            try {
                val materiasList = repository.getMaterias()
                _materias.value = materiasList
                _materiasMap.value = materiasList.associate { it.id to it.nombre }
                _tutors.value = repository.getTutors()
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar datos: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}

class MainEstudianteViewModelFactory(
    private val repository: EstudianteRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(MainEstudianteViewModel::class.java)) {
            return MainEstudianteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

