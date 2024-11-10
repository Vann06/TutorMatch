package com.example.tutormatch.ui.tutor.Estudiante_Tu.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaConDetalles
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EstudianteTuViewModel : ViewModel() {

    private val _tutoriaConDetalles = MutableStateFlow<TutoriaConDetalles?>(null)
    val tutoriaConDetalles: StateFlow<TutoriaConDetalles?> get() = _tutoriaConDetalles

    fun cargarDetalleTutoria(tutoriaId: String) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()

            try {
                // Obtener la tutoría
                val tutoriaSnapshot = firestore.collection("tutorias").document(tutoriaId).get().await()
                val tutoria = tutoriaSnapshot.toObject(Tutoria1::class.java)

                if (tutoria != null) {
                    // Obtener el estudiante
                    val estudianteSnapshot = firestore.collection("estudiantes").document(tutoria.estudianteId).get().await()
                    val estudiante = estudianteSnapshot.toObject(Estudiante1::class.java)

                    // La materia es parte del tutoria.materiaId
                    val materia = Materia(id = tutoria.materiaId, nombre = tutoria.materiaId)

                    if (estudiante != null) {
                        val detalle = TutoriaConDetalles(tutoria, estudiante, materia)
                        _tutoriaConDetalles.value = detalle
                    } else {
                        // Manejar error
                    }
                } else {
                    // Manejar error
                }
            } catch (e: Exception) {
                // Manejar excepción
            }
        }
    }

    fun aceptarSolicitud(tutoriaId: String) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("tutorias").document(tutoriaId)
                .update("estado", "Aceptada")
                .await()
        }
    }
    fun rechazarSolicitud(tutoriaId: String) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("tutorias").document(tutoriaId)
                .update("estado", "Rechazada")
                .await()
        }
    }
}
