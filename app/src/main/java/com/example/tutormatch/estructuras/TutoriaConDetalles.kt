package com.example.tutormatch.estructuras

import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1

data class TutoriaConDetalles (
    val tutoria: Tutoria1,
    val estudiante: Estudiante1,
    val materia: Materia
)