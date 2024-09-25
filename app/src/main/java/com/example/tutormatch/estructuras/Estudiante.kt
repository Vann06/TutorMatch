package com.example.tutormatch.estructuras

data class Estudiante(
    val id: String = "",
    var nombre: String = "",
    var usuario: String = "",
    var contraseña: String = "",
    val notificaciones: List<String> = emptyList(),
    var myTutors: List<String> = emptyList(), // IDs de los tutores
    var fotoPerfil: String = "" // URL a la imagen del perfil
)
