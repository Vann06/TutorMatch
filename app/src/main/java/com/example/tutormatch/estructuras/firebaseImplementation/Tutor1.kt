package com.example.tutormatch.estructuras.firebaseImplementation

data class Tutor1(
    override val id: String = "",
    override var nombre: String = "",
    override var usuario: String = "",
    override var fotoPerfilUrl: String = "",
    val email: String = "",
    val materias: List<String> = listOf(),
    val modalidad: String = "",
    val descripcion: String = ""
) : Usuario1
