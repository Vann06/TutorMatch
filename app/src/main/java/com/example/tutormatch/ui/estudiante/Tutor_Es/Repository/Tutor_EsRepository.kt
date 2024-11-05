package com.example.tutormatch.ui.estudiante.Tutor_Es.Repository

import android.util.Log
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PerfilTutorEstudianteRepository(private val firestore: FirebaseFirestore) {

    suspend fun getTutorById(tutorId: String): Tutor1? {
        return try {
            val documentSnapshot = firestore.collection("tutores").document(tutorId).get().await()
            val tutor = documentSnapshot.toObject(Tutor1::class.java)
            if (tutor != null) {
                tutor.copy(id = documentSnapshot.id)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("PerfilTutorEstudianteRepo", "Error al obtener tutor", e)
            null
        }
    }
}