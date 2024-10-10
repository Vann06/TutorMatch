package com.example.tutormatch.estructuras
import com.example.tutormatch.R
import kotlinx.serialization.Serializable

@Serializable
data class Estudiante(
    override val id: String = "",
    override var nombre: String = "Nicolas Maduro",
    override var usuario: String = "maduro23",
    override var contraseña: String = "si",
    val notificaciones: List<String> = emptyList(),
    var misTutorias: MutableList<Tutoria> = mutableListOf(
        Tutoria(),
        Tutoria(tutor=Tutor(nombre="Diego López"))
    ),
    override var fotoPerfil: Int = R.drawable.estudiante,
    var myTutors: MutableList<Tutor> = mutableListOf()
) : Usuario


