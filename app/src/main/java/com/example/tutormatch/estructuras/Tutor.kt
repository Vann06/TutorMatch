package com.example.tutormatch.estructuras

import com.example.tutormatch.R
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import kotlinx.serialization.Serializable

@Serializable
data class Tutor(
    override val id: String = "",
    override var nombre: String = "Ricardo Godínez",
    override var usuario: String = "ricgod213",
    override var contraseña: String = "si",
    var myStudents: List<Estudiante> = listOf(), // List en lugar de MutableList
    override var fotoPerfil: Int? = R.drawable.tutor, // Cambiado a Int?
    var materias: List<Materia> = listOf(), // List en lugar de MutableList
    var descripcion: String = "Soy el tutor más basado",
    var modalidad: String = "Presencial/Virtual"
) : Usuario
