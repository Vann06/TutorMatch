package com.example.tutormatch.ui.tutor.solicitudes.ViewModel

import androidx.lifecycle.ViewModel
import com.example.tutormatch.estructuras.Estudiante1
import com.example.tutormatch.estructuras.Tutoria1
import com.example.tutormatch.estructuras.TutoriaConDetalles
import com.example.tutormatch.estructuras.Materia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TutoriaViewModel: ViewModel(){

    private val _solicitudesTutoria = MutableStateFlow<List<TutoriaConDetalles>>(emptyList())
    val solicitudesTutoria: StateFlow<List<TutoriaConDetalles>> get()= _solicitudesTutoria

    init{
        cargarSolicitudesSimuladas()
    }

    //empezar estructura para las corrrutinas

    private fun cargarSolicitudesSimuladas(){

        val estudiante1 = Estudiante1(
            id = "est1",
            nombre = "Juan Pérez",
            fotoPerfilUrl = ""
        )
        val estudiante2 = Estudiante1(
            id = "est2",
            nombre = "María Gómez",
        )

        // Datos simulados de materias
        val materia1 = Materia(
            id = "mat1",
            nombre = "Matemáticas"
        )
        val materia2 = Materia(
            id = "mat2",
            nombre = "Física"

        )

        // Datos simulados de tutorías
        val tutoria1 = Tutoria1(
            id = "tut1",
            estudianteId = "est1",
            tutorId = "tutor1",
            materiaId = "mat1",
            fecha = "2024-10-15",
            hora = "10:00 AM",
            modalidad = "Virtual",
            mensaje = "Necesito ayuda con integrales.",
            estado = "pendiente"
        )
        val tutoria2 = Tutoria1(
            id = "tut2",
            estudianteId = "est2",
            tutorId = "tutor1",
            materiaId = "mat2",
            fecha = "2024-10-16",
            hora = "2:00 PM",
            modalidad = "Presencial",
            mensaje = "",
            estado = "pendiente"
        )

        val solicitudes = listOf(
            TutoriaConDetalles(tutoria1, estudiante1, materia1),
            TutoriaConDetalles(tutoria2, estudiante2, materia2)
        )

        _solicitudesTutoria.value = solicitudes
    }

    fun aceptarTutoria(tutoriaId: String){
        println("Tutoria $tutoriaId aceptada")
    }

    fun rechazarTutoria(tutoriaId: String){
        println("Tutoria $tutoriaId rechazada")
    }

}