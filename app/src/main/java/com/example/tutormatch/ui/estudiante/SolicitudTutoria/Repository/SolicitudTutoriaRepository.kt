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
/*
    suspend fun getMateriasByIds(materiasIds: List<String>): Result<List<Materia>> {
        println("Consultando materias con IDs: $materiasIds")
        return try {
            val materias = mutableListOf<Materia>()
            val batchSize = 10 // Firestore limita 'whereIn' a 10 elementos
            val chunks = materiasIds.chunked(batchSize)
            for (chunk in chunks) {
                println("Consultando chunk de IDs: $chunk")
                val snapshot = firestore.collection("materias")
                    .whereIn(FieldPath.documentId(), chunk)
                    .get()
                    .await()
                val materiasChunk = snapshot.documents.mapNotNull { document ->
                    val materia = document.toObject(Materia::class.java)
                    materia?.apply { id = document.id } // Asignar el ID del documento a la materia
                }
                println("Materias obtenidas en este chunk: $materiasChunk")
                materias.addAll(materiasChunk)
            }
            Result.success(materias)
        } catch (e: Exception) {
            println("Error al obtener materias: ${e.message}")
            Result.failure(e)
        }
    }

 */

    /*
    suspend fun getMateriasByNombres(materiasNombres: List<String>): Result<List<Materia>> {
        println("Consultando materias con nombres: $materiasNombres")
        return try {
            val materias = mutableListOf<Materia>()
            val batchSize = 10 // Firestore limita 'whereIn' a 10 elementos
            val chunks = materiasNombres.chunked(batchSize)
            for (chunk in chunks) {
                println("Consultando chunk de nombres: $chunk")
                val snapshot = firestore.collection("materias")
                    .whereIn("nombre", chunk)
                    .get()
                    .await()
                val materiasChunk = snapshot.documents.mapNotNull { document ->
                    val materia = document.toObject(Materia::class.java)
                    materia?.apply { id = document.id } // Asignar el ID del documento
                }
                println("Materias obtenidas en este chunk: $materiasChunk")
                materias.addAll(materiasChunk)
            }
            Result.success(materias)
        } catch (e: Exception) {
            println("Error al obtener materias: ${e.message}")
            Result.failure(e)
        }
    }
    suspend fun getAllMaterias(): List<Materia> {
        return try {
            val snapshot = firestore.collection("materias")
                .get()
                .await()
            val materias = snapshot.documents.mapNotNull { document ->
                val materia = document.toObject(Materia::class.java)
                materia?.apply { id = document.id }
            }
            println("Todas las materias en Firestore: $materias")
            materias
        } catch (e: Exception) {
            println("Error al obtener todas las materias: ${e.message}")
            emptyList()
        }
    }
*/


    // Crear una nueva tutoría
    suspend fun createTutoria(tutoria: Tutoria1): Result<Unit> {
        return try {
            firestore.collection("tutorias").document(tutoria.id).set(tutoria).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
