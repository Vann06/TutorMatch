package com.example.tutormatch.ui.estudiante.MyTutors.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.ui.estudiante.MyTutors.Repository.MyTutorsRepository
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.asLiveData
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyTutorsViewModel(
    private val repository: MyTutorsRepository = MyTutorsRepository()
) : ViewModel() {

    private val _tutoriasIndividuales = MutableStateFlow<List<Tutoria1>>(emptyList())
    val tutoriasIndividuales: StateFlow<List<Tutoria1>> get() = _tutoriasIndividuales

    private val _tutoriasGrupales = MutableStateFlow<List<TutoriaGrupal>>(emptyList())
    val tutoriasGrupales: StateFlow<List<TutoriaGrupal>> get() = _tutoriasGrupales

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // Obtener el ID del estudiante autenticado
    private val estudianteId: String? = FirebaseAuth.getInstance().currentUser?.uid

    init {
        if (estudianteId != null) {
            loadTutorias()
        } else {
            _error.value = "Estudiante no autenticado"
        }
    }

    private fun loadTutorias() {
        viewModelScope.launch {
            try {
                val individuales = repository.getTutoriasIndividualesByEstudiante(estudianteId!!)
                _tutoriasIndividuales.value = individuales

                val grupales = repository.getTutoriasGrupalesByEstudiante(estudianteId)
                _tutoriasGrupales.value = grupales
            } catch (e: Exception) {
                _error.value = "Error al cargar tutorías: ${e.message}"
            }
        }
    }
}
