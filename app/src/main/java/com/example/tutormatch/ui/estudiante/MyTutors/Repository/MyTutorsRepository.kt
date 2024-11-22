package com.example.tutormatch.ui.estudiante.MyTutors.Repository

import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MyTutorsRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getTutoriasIndividualesByEstudiante(estudianteId: String): List<Tutoria1> {
        return db.collection("tutorias_individuales")
            .whereEqualTo("estudianteId", estudianteId)
            .whereEqualTo("estado", "Aceptada")
            .get()
            .await()
            .toObjects(Tutoria1::class.java)
    }

    suspend fun getTutoriasGrupalesByEstudiante(estudianteId: String): List<TutoriaGrupal> {
        return db.collection("tutorias_grupales")
            .whereArrayContains("estudiantesInscritos", estudianteId)
            .whereEqualTo("estado", "Disponible")
            .get()
            .await()
            .toObjects(TutoriaGrupal::class.java)
    }
}
