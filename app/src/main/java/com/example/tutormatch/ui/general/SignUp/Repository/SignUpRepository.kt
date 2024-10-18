package com.example.tutormatch.ui.general.SignUp.Repository



import com.example.tutormatch.estructuras.Estudiante1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    suspend fun signUp(email: String, password: String, name: String, accountType: String): Result<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("Error al obtener el ID del usuario")

            // Guardar el usuario en Firestore
            val estudiante = Estudiante1(
                id = userId,
                nombre = name,
                email = email, // Suponiendo que el usuario es el email
            )

            firestore.collection("estudiantes").document(userId).set(estudiante).await()
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
