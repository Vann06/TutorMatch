package com.example.tutormatch.estructuras
import com.example.tutormatch.R
import kotlinx.serialization.Serializable

@Serializable
data class Tutor(
    override val id: String = "",
    override var nombre: String = "Ricardo Godínez",
    override var usuario: String = "ricgod213",
    override var contraseña: String = "si",
    var myStudents: MutableList<Estudiante> = mutableListOf(),
    override var fotoPerfil: Int = R.drawable.tutor,
    var materias: MutableList<Materias> = mutableListOf(),
    var descripcion: String = "Soy el tutor más basado",
    var modalidad: String = "Presencial/Virtual",
) : Usuario