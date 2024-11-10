package com.example.tutormatch.estructuras.firebaseImplementation

import kotlinx.serialization.Serializable

@Serializable
data class Materia(
    var id: String = "",
    val nombre: String = ""
)