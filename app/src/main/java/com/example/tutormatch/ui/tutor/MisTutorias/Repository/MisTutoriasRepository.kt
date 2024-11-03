package com.example.tutormatch.ui.tutor.MisTutorias.Repository

import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PerfilTutorRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun obtenerTutorActual(): Tutor1? {
        val userId = auth.currentUser?.uid ?: return null
        val snapshot = firestore.collection("tutores").document(userId).get().await()
        return snapshot.toObject(Tutor1::class.java)
    }

    suspend fun actualizarTutor(tutor: Tutor1) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("tutores").document(userId).set(tutor).await()
    }
}
