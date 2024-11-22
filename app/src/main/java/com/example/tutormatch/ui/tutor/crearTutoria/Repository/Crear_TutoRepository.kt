package com.example.tutormatch.ui.tutor.crearTutoria.Repository

import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CrearTutoriaRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun obtenerMaterias(): List<Materia> {
        return try {
            val snapshot = firestore.collection("materias").get().await()
            snapshot.toObjects(Materia::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerTutorPorId(tutorId: String): Result<Tutor1> {
        return try {
            val snapshot = firestore.collection("tutores").document(tutorId).get().await()
            val tutor = snapshot.toObject(Tutor1::class.java)
            if (tutor != null) {
                Result.success(tutor)
            } else {
                Result.failure(Exception("Tutor no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Cambiamos la función para aceptar TutoriaGrupal
    suspend fun crearTutoriaGrupal(tutoria: TutoriaGrupal): Result<String> {
        return try {
            val documentRef =
                firestore.collection("tutorias_grupales").document() // Usamos la nueva colección
            tutoria.id = documentRef.id
            documentRef.set(tutoria).await()
            Result.success("Tutoría grupal creada con éxito")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
