package com.example.tutormatch.estructuras.firebaseImplementation

import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1

data class TutoriaConDetallesEstudiante(
    val tutoria: Tutoria1,
    val tutor: Tutor1,
    val materia: Materia,
    )
