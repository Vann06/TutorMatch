package com.example.tutormatch.ui.estudiante.MyTutors.Repository

import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MyTutorsRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getTutoriasByEstudiante(estudianteId: String): Flow<List<Tutoria1>> = callbackFlow {
        val listenerRegistration = db.collection("tutorias")
            .whereEqualTo("estudianteId", estudianteId)
            .whereEqualTo("estado", "Aceptada")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val tutorias = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Tutoria1::class.java)
                    }
                    trySend(tutorias)
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}
