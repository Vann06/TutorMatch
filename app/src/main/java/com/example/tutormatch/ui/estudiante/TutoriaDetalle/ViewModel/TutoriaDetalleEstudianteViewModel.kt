package com.example.tutormatch.ui.estudiante.TutoriaDetalle.ViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaConDetallesEstudiante
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class TutoriaDetalleEstudianteViewModel : ViewModel() {

    private val _tutoriaConDetalles = MutableStateFlow<TutoriaConDetallesEstudiante?>(null)
    val tutoriaConDetalles: StateFlow<TutoriaConDetallesEstudiante?> get() = _tutoriaConDetalles

    fun cargarDetalleTutoria(tutoriaId: String) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()

            try {
                // Obtener la tutoría
                val tutoriaSnapshot = firestore.collection("tutorias").document(tutoriaId).get().await()
                val tutoria = tutoriaSnapshot.toObject(Tutoria1::class.java)

                if (tutoria != null) {
                    // Obtener el tutor
                    val tutorSnapshot = firestore.collection("tutores").document(tutoria.tutorId).get().await()
                    val tutor = tutorSnapshot.toObject(Tutor1::class.java)

                    // La materia es parte del tutoria.materiaId
                    val materia = Materia(id = tutoria.materiaId, nombre = tutoria.materiaId)

                    if (tutor != null) {
                        val detalle = TutoriaConDetallesEstudiante(tutoria, tutor, materia)
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

    fun cancelarTutoria(tutoriaId: String) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("tutorias").document(tutoriaId)
                .update("estado", "Cancelada")
                .await()
        }
    }
}
