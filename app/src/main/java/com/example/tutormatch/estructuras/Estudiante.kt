package com.example.tutormatch.estructuras
import kotlinx.serialization.Serializable

@Serializable
data class Estudiante(
    val id: String = "",
    var nombre: String = "",
    var usuario: String = "",
    var contraseña: String = "",
    val notificaciones: List<String> = emptyList(),
    var myTutors: MutableList<Tutor>,
    var fotoPerfil: String = ""
)
