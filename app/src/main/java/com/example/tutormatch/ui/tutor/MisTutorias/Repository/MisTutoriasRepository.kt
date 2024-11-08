package com.example.tutormatch.ui.tutor.MisTutorias.Repository

import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PerfilTutorRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun obtenerTutorActual(): Tutor1? {
        return try {
            val userId = auth.currentUser?.uid ?: return null
            val snapshot = firestore.collection("tutores").document(userId).get().await()
            snapshot.toObject(Tutor1::class.java)
        } catch (e: Exception) {
            null // Devuelve null si ocurre algún error
        }
    }

    suspend fun actualizarTutor(tutor: Tutor1): Boolean {
        return try {
            val userId = auth.currentUser?.uid ?: return false
            firestore.collection("tutores").document(userId).set(tutor).await()
            true
        } catch (e: Exception) {
            false // Devuelve false si la actualización falla
        }
    }
}
