package com.example.tutormatch.estructuras.firebaseImplementation


sealed class TutoriaDetalles{
    data class TutoriaConDetalles (
        val tutoria: Tutoria1,
        val estudiante: Estudiante1,
        val materia: Materia
    ): TutoriaDetalles()

    data class TutoriaGrupalDetalles(
        val tutoria: TutoriaGrupal,
        val tutor: Tutor1,
        val materia: Materia,
        val estudiantes: List<Estudiante1>

    ): TutoriaDetalles()


}
