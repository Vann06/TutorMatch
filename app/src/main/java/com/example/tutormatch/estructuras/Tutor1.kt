package com.example.tutormatch.estructuras

class Tutor1 (
    override val id: String = "",
    override var nombre: String = "",
    override var usuario: String = "",
    override var fotoPerfilUrl: String = "",
    val email: String = "",
    var descripcion: String = "",
    var modalidad: String = "",
    val materiasIds: List<String> = listOf(),
    val estudiantesIds: List<String> = listOf()
) : Usuario1