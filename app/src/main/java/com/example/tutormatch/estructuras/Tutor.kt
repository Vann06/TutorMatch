package com.example.tutormatch.estructuras
import kotlinx.serialization.Serializable

@Serializable
data class Tutor(
    val id: String = "",
    var nombre: String = "",
    var usuario: String = "",
    var contraseña: String = "",
    var myStudents: MutableList<Estudiante>,
    var fotoPerfil: Int,
    var materias: MutableList<Materias>,
    var descripcion: String = "",
    var modalidad: String = "",
)