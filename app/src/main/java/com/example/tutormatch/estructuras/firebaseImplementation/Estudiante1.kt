package com.example.tutormatch.estructuras.firebaseImplementation

// Estudiante1.kt
class Estudiante1 (
    override val id: String = "",
    override var nombre: String = "",
    override var usuario: String = "",
    override var fotoPerfilUrl: String = "",
    val email: String = "",
    val tutoresIds: List<String> = listOf()
) : Usuario1 {

    fun copy(
        id: String = this.id,
        nombre: String = this.nombre,
        usuario: String = this.usuario,
        fotoPerfilUrl: String = this.fotoPerfilUrl,
        email: String = this.email,
        tutoresIds: List<String> = this.tutoresIds
    ): Estudiante1 {
        return Estudiante1(id, nombre, usuario, fotoPerfilUrl, email, tutoresIds)
    }
}
