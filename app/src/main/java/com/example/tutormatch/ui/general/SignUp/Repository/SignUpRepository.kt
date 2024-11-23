package com.example.tutormatch.ui.general.SignUp.Repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signUp(email: String, password: String, name: String, accountType: String): Result<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: throw Exception("Error al obtener el ID del usuario")

            // Crear un mapa de datos de usuario
            val user = hashMapOf(
                "id" to userId,
                "nombre" to name,
                "email" to email,
                "tipoCuenta" to accountType
            )

            // Guardar el usuario en la colección correspondiente
            val collectionName = when (accountType) {
                "Estudiante" -> "estudiantes"
                "Tutor" -> "tutores"
                else -> "usuarios"
            }

            firestore.collection(collectionName).document(userId).set(user).await()

            Result.success(userId)
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception("Error de autenticación: ${e.message}"))
        } catch (e: FirebaseFirestoreException) {
            Result.failure(Exception("Error al guardar en Firestore: ${e.message}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
