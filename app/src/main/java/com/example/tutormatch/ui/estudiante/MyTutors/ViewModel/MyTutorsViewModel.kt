package com.example.tutormatch.ui.estudiante.MyTutors.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1

class MyTutorsViewModel : ViewModel() {
    private val _tutorias = MutableLiveData<List<Tutoria1>>()
    val tutorias: LiveData<List<Tutoria1>> = _tutorias

    private val _estudiante = MutableLiveData<Estudiante1>()
    val estudiante: LiveData<Estudiante1> = _estudiante

    init {
        loadTutorias()
    }

    private fun loadTutorias() {
        viewModelScope.launch {
            delay(2000) // Simulamos un retraso en la carga

            val exampleEstudiante = Estudiante1(
                id = "exampleEstudianteId",
                nombre = "Ejemplo Estudiante",
                usuario = "ejemploEstudiante",
                fotoPerfilUrl = "url_de_foto_perfil",
                email = "ejemplo@correo.com",
                tutoresIds = listOf("tutorId1", "tutorId2")
            )

            val exampleTutorias = listOf(
                Tutoria1(
                    id = "1",
                    estudianteId = exampleEstudiante.id,
                    tutorId = "tutorId1",
                    materiaId = "materiaId1",
                    modalidad = "Virtual",
                    fecha = "02/10/24",
                    hora = "17:35",
                    mensaje = "Consulta sobre Física 1",
                    estado = "Pendiente"
                ),
                Tutoria1(
                    id = "2",
                    estudianteId = exampleEstudiante.id,
                    tutorId = "tutorId2",
                    materiaId = "materiaId2",
                    modalidad = "Presencial",
                    fecha = "03/10/24",
                    hora = "14:00",
                    mensaje = "Consulta sobre Matemáticas",
                    estado = "Pendiente"
                )
            )

            // Asignamos los datos al LiveData
            _estudiante.value = exampleEstudiante
            _tutorias.value = exampleTutorias
        }
    }
}
