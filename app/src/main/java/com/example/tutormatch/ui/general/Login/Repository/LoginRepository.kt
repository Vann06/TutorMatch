package com.example.tutormatch.ui.general.Login.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LoginRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("Usuario no encontrado")

            // Obtener el tipo de cuenta desde Firestore
            val accountType = getAccountType(userId)

            Result.success(accountType)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getAccountType(userId: String): String {
        // Intentar obtener el usuario como estudiante
        val estudianteSnapshot = firestore.collection("estudiantes").document(userId).get().await()
        if (estudianteSnapshot.exists()) {
            return "Estudiante"
        }

        // Si no es estudiante, intentar obtener el usuario como tutor
        val tutorSnapshot = firestore.collection("tutores").document(userId).get().await()
        if (tutorSnapshot.exists()) {
            return "Tutor"
        }

        throw Exception("Tipo de cuenta no encontrado")
    }
}
