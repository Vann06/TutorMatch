package com.example.tutormatch.ui.estudiante.Tutor_Es.Repository

import android.util.Log
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PerfilTutorEstudianteRepository(private val firestore: FirebaseFirestore) {

    suspend fun getTutoriasGrupales(tutorId: String): List<TutoriaGrupal> {
        return try {
            val snapshot = firestore.collection("tutorias_grupales")
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("estado", "Disponible") // Agregamos el filtro aquí
                .get()
                .await()
            snapshot.toObjects(TutoriaGrupal::class.java)
        } catch (e: Exception) {
            Log.e("PerfilTutorEstudianteRepo", "Error al obtener tutorías grupales", e)
            emptyList()
        }
    }
    suspend fun getTutorById(tutorId: String): Tutor1? {
        return try {
            val documentSnapshot = firestore.collection("tutores").document(tutorId).get().await()
            val tutor = documentSnapshot.toObject(Tutor1::class.java)
            tutor?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e("PerfilTutorEstudianteRepo", "Error al obtener tutor", e)
            null
        }
    }
}