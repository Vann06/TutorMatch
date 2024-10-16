package com.example.tutormatch.estructuras

import kotlinx.serialization.Serializable

@Serializable
data class Tutoria (
    val id: String = "1",
    val fecha: String = "14/10/24",
    val hora: String = "18:00",
    val modalidad: String = "Presencial",
    var tutor: Tutor? = null,
    val materia: Materias = Materias(),
    var estudiante: Estudiante? = null


)

