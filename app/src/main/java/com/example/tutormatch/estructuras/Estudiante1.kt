package com.example.tutormatch.estructuras

class Estudiante1 (
    override val id: String = "",
    override var nombre: String = "",
    override var usuario: String = "",
    override var fotoPerfilUrl: String = "",
    val email: String = "",
    val tutoresIds: List<String> = listOf()
): Usuario1