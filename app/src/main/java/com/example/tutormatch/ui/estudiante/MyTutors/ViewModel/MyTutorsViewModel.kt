package com.example.tutormatch.ui.estudiante.MyTutors.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tutormatch.estructuras.Estudiante
import com.example.tutormatch.estructuras.Materia
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.estructuras.Tutoria

class MyTutorsViewModel : ViewModel() {
    private val _tutorias = MutableLiveData<Estudiante>()
    val tutoriaEstudiante: LiveData<Estudiante> = _tutorias

    init {
        loadTutorias()
    }

    private fun loadTutorias() {
        viewModelScope.launch {
            // Simula una carga de datos con retraso
            delay(2000)

            val exampleEstudiante = Estudiante(
                nombre = "Ejemplo Estudiante",
                misTutorias = mutableListOf(
                    Tutoria(
                        tutor = Tutor(nombre = "Ricardo Godinez"),
                        modalidad = "Virtual",
                        materia = Materia(nombre = "Física 1"),
                        fecha = "02/10/24",
                        hora = "17:35"
                    ),
                    Tutoria(
                        tutor = Tutor(nombre = "Diego López"),
                        modalidad = "Presencial",
                        materia = Materia(nombre = "Matemáticas"),
                        fecha = "03/10/24",
                        hora = "14:00"
                    )
                )
            )

            _tutorias.value = exampleEstudiante
        }
    }
}
