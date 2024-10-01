package com.example.tutormatch.estructuras
import kotlinx.serialization.Serializable

@Serializable
data class Estudiante(
    val id: String = "",
    var nombre: String = "Nicolas Maduro",
    var usuario: String = "maduro23",
    var contraseña: String = "si",
    val notificaciones: List<String> = emptyList(),
    var misTutorias: MutableList<Tutoria> = mutableListOf(
        Tutoria(),
        Tutoria(tutor=Tutor(nombre="Diego López"))
    ),
    var fotoPerfil: String = "",
    var myTutors: MutableList<Tutor> = mutableListOf()
)


