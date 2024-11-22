package com.example.tutormatch.ui.estudiante.SolicitudTutoria

import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FieldPath


class SolicitudTutoriaRepository {

    private val firestore = FirebaseFirestore.getInstance()

    // Obtener el tutor por ID
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

    // Actualizar una tutoría individual existente
    suspend fun updateTutoriaIndividual(tutoria: Tutoria1): Result<Unit> {
        return try {
            firestore.collection("tutorias_individuales")
                .document(tutoria.id)
                .set(tutoria)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Cancelar una tutoría individual
    suspend fun cancelTutoriaIndividual(tutoriaId: String): Result<Unit> {
        return try {
            firestore.collection("tutorias_individuales")
                .document(tutoriaId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    // Crear una nueva tutoría
    suspend fun createTutoriaIndividual(tutoria: Tutoria1): Result<Unit> {
        return try {
            firestore.collection("tutorias_individuales") // Cambiar a 'tutorias_individuales'
                .document(tutoria.id)
                .set(tutoria)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
