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

class MyTutorsViewModel(
    private val repository: MyTutorsRepository = MyTutorsRepository()
) : ViewModel() {

    val tutorias: LiveData<List<Tutoria1>>
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // Obtener el ID del estudiante autenticado
    private val estudianteId: String? = FirebaseAuth.getInstance().currentUser?.uid

    init {
        if (estudianteId != null) {
            tutorias = repository.getTutoriasByEstudiante(estudianteId)
                .asLiveData()
        } else {
            tutorias = MutableLiveData()
            _error.value = "Estudiante no autenticado"
        }
    }
}
