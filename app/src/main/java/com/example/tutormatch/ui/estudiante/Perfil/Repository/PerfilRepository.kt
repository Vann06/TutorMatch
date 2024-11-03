package com.example.tutormatch.ui.estudiante.Perfil.Repository

import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PerfilEstudianteRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun obtenerEstudianteActual(): Estudiante1? {
        val userId = auth.currentUser?.uid ?: return null
        val snapshot = firestore.collection("estudiantes").document(userId).get().await()
        return snapshot.toObject(Estudiante1::class.java)
    }

    suspend fun actualizarEstudiante(estudiante: Estudiante1) {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("estudiantes").document(userId).set(estudiante).await()
    }
}
