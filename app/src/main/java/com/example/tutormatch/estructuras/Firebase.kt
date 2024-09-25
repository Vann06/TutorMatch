package com.example.tutormatch.estructuras

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

val db = FirebaseFirestore.getInstance()
val userId = FirebaseAuth.getInstance().currentUser?.uid

fun obtenerPerfilEstudiante(userId: String, onSuccess: (Estudiante) -> Unit, onFailure: (Exception) -> Unit) {
    db.collection("estudiantes").document(userId).get()
        .addOnSuccessListener { document ->
            val estudiante = document.toObject(Estudiante::class.java)
            if (estudiante != null) {
                onSuccess(estudiante)
            }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}
