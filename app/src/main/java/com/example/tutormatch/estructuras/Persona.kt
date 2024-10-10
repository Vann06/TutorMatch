package com.example.tutormatch.estructuras;

import kotlinx.serialization.Serializable;

interface Usuario {
    val id: String
    var nombre: String
    var usuario: String
    var contraseña: String
    var fotoPerfil: Int
}