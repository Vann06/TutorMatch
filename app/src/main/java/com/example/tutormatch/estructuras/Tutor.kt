package com.example.tutormatch.estructuras
import com.example.tutormatch.R
import kotlinx.serialization.Serializable

@Serializable
data class Tutor(
    val id: String = "",
    var nombre: String = "Ricardo Godínez",
    var usuario: String = "ricgod213",
    var contraseña: String = "si",
    var myStudents: MutableList<Estudiante> = mutableListOf(),
    var fotoPerfil: Int = R.drawable.estudiante,
    var materias: MutableList<Materias> = mutableListOf(),
    var descripcion: String = "Soy el tutor más basado",
    var modalidad: String = "Presencial/Virtual",
)