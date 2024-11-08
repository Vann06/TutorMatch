package com.example.tutormatch.ui.estudiante.MyTutors.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.ui.estudiante.MyTutors.Repository.MyTutorsRepository
import kotlinx.coroutines.launch

class MyTutorsViewModel(
    private val repository: MyTutorsRepository = MyTutorsRepository()
) : ViewModel() {

    private val _tutorias = MutableLiveData<List<Tutoria1>>()
    val tutorias: LiveData<List<Tutoria1>> get() = _tutorias

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val estudianteId = "ID_ESTUDIANTE_EJEMPLO"

    init {
        loadTutorias()
    }

    private fun loadTutorias() {
        viewModelScope.launch {
            try {
                val result = repository.getTutoriasByEstudiante(estudianteId)
                _tutorias.value = result
            } catch (exception: Exception) {
                // Manejo de error
                _error.value = "Error al cargar las tutorías: ${exception.message}"
            }
        }
    }
}
