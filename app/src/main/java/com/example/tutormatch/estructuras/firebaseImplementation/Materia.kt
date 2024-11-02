package com.example.tutormatch.estructuras.firebaseImplementation

import kotlinx.serialization.Serializable

@Serializable
data class Materia(
    val id: String = "",
    val nombre: String = ""
)