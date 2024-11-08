package com.example.tutormatch.ui.estudiante.MyTutors.Repository

import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MyTutorsRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getTutoriasByEstudiante(estudianteId: String): List<Tutoria1> {
        return try {
            val snapshot = db.collection("tutorias")
                .whereEqualTo("estudianteId", estudianteId)
                .get()
                .await()

            snapshot.documents.map { doc ->
                doc.toObject(Tutoria1::class.java)!!
            }
        } catch (e: Exception) {
            // Manejo de error
            throw Exception("Error al obtener tutorías: ${e.message}")
        }
    }
}
