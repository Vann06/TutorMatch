package com.example.tutormatch.ui.tutor.crearTutoria.Repository

// CrearTutoriaRepository.kt
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
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

    suspend fun crearTutoria(tutoria: Tutoria1): Result<String> {
        return try {
            val documentRef = firestore.collection("tutorias").document()
            tutoria.id = documentRef.id
            documentRef.set(tutoria).await()
            Result.success("Tutoría creada con éxito")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
