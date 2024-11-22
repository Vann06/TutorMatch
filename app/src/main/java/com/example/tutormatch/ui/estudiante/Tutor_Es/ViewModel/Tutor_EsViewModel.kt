package com.example.tutormatch.ui.estudiante.Tutor_Es.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.example.tutormatch.ui.estudiante.Tutor_Es.Repository.PerfilTutorEstudianteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilTutorEstudianteViewModel(private val tutorId: String) : ViewModel() {

    private val repository = PerfilTutorEstudianteRepository(FirebaseFirestore.getInstance())

    private val _tutor = MutableStateFlow<Tutor1?>(null)
    val tutor: StateFlow<Tutor1?> = _tutor

    private val _tutoriasGrupales = MutableStateFlow<List<TutoriaGrupal>>(emptyList())
    val tutoriasGrupales: StateFlow<List<TutoriaGrupal>> = _tutoriasGrupales

    init {
        loadTutorData()
        loadTutoriasGrupales()
    }

    private fun loadTutorData() {
        viewModelScope.launch {
            val tutorData = repository.getTutorById(tutorId)
            _tutor.value = tutorData
        }
    }

    private fun loadTutoriasGrupales() {
        viewModelScope.launch {
            val tutorias = repository.getTutoriasGrupales(tutorId)
            // Filtrar tutorías que no estén canceladas
            val tutoriasDisponibles = tutorias.filter { it.estado != "Cancelada" }
            _tutoriasGrupales.value = tutorias
        }
    }
}

class PerfilTutorEstudianteViewModelFactory(private val tutorId: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilTutorEstudianteViewModel::class.java)) {
            return PerfilTutorEstudianteViewModel(tutorId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}