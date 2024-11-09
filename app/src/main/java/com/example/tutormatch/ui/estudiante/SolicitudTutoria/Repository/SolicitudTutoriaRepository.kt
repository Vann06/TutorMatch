package com.example.tutormatch.ui.estudiante.SolicitudTutoria

import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SolicitudTutoriaRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val tutoriaCollection = firestore.collection("tutorias")
    private val materiasCollection = firestore.collection("materias") // Nueva colección de materias

    // Crear una nueva tutoría en Firestore
    suspend fun createTutoria(tutoria: Tutoria1): Result<Unit> {
        return try {

            tutoriaCollection.add(tutoria).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener una lista de todas las tutorías
    suspend fun getTutorias(): Result<List<Tutoria1>> {
        return try {
            val snapshot = tutoriaCollection.get().await()
            val tutorias = snapshot.documents.mapNotNull { it.toObject(Tutoria1::class.java) }
            Result.success(tutorias)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener una tutoría específica por ID
    suspend fun getTutoriaById(id: String): Result<Tutoria1?> {
        return try {
            val document = tutoriaCollection.document(id).get().await()
            Result.success(document.toObject(Tutoria1::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // *** Nuevos métodos para Materia ***

    // Agregar una nueva materia
    suspend fun agregarMateria(materia: Materia): Result<Unit> {
        return try {
            materiasCollection.document(materia.id).set(materia).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener una lista de todas las materias
    suspend fun getMaterias(): Result<List<Materia>> {
        return try {
            val snapshot = materiasCollection.get().await()
            val materias = snapshot.documents.mapNotNull { it.toObject(Materia::class.java) }
            Result.success(materias)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener una materia específica por ID
    suspend fun getMateriaById(id: String): Result<Materia?> {
        return try {
            val document = materiasCollection.document(id).get().await()
            Result.success(document.toObject(Materia::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
