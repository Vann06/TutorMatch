package com.example.tutormatch.estructuras.firebaseImplementation

data class TutoriaGrupal(
    var id: String = "",
    val tutorId: String = "",
    val materiaId: String = "",
    val fecha: String = "",
    val hora: String = "",
    val modalidad: String = "",
    val mensaje: String = "",
    val estado: String = "Disponible",
    val cuposMaximos: Int = 15, //Cuantos estudiantes pueden ir o permitidos
    val estudiantesInscritos: MutableList<String> = mutableListOf() //ID de los estudiantes ya inscritos
)