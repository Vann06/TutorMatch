package com.example.tutormatch.ui.estudiante.TutoriaGrupal.TutoriaGrupalViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class DetalleTutoriaGrupalViewModel(private val tutoriaId: String) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _tutoriaGrupal = MutableStateFlow<TutoriaGrupal?>(null)
    val tutoriaGrupal: StateFlow<TutoriaGrupal?> = _tutoriaGrupal

    private val _tutor = MutableStateFlow<Tutor1?>(null)
    val tutor: StateFlow<Tutor1?> = _tutor

    private val _isEstudianteInscrito = MutableStateFlow(false)
    val isEstudianteInscrito: StateFlow<Boolean> = _isEstudianteInscrito

    init {
        cargarDetalleTutoriaGrupal()
    }

    private fun cargarDetalleTutoriaGrupal() {
        viewModelScope.launch {
            try {
                val tutoriaSnapshot = firestore.collection("tutorias_grupales").document(tutoriaId).get().await()
                val tutoria = tutoriaSnapshot.toObject(TutoriaGrupal::class.java)
                _tutoriaGrupal.value = tutoria

                tutoria?.let {
                    val tutorSnapshot = firestore.collection("tutores").document(it.tutorId).get().await()
                    val tutorData = tutorSnapshot.toObject(Tutor1::class.java)
                    _tutor.value = tutorData

                    val estudianteId = auth.currentUser?.uid
                    _isEstudianteInscrito.value = estudianteId != null && it.estudiantesInscritos.contains(estudianteId)
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun inscribirseEnTutoriaGrupal() {
        viewModelScope.launch {
            val estudianteId = auth.currentUser?.uid ?: return@launch
            val tutoriaRef = firestore.collection("tutorias_grupales").document(tutoriaId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(tutoriaRef)
                val tutoria = snapshot.toObject(TutoriaGrupal::class.java) ?: return@runTransaction

                if (tutoria.estudiantesInscritos.contains(estudianteId)) {
                    // Ya está inscrito
                    return@runTransaction
                }

                if (tutoria.estudiantesInscritos.size >= tutoria.cuposMaximos) {
                    // No hay cupos disponibles
                    return@runTransaction
                }

                tutoria.estudiantesInscritos.add(estudianteId)
                transaction.update(tutoriaRef, "estudiantesInscritos", tutoria.estudiantesInscritos)
            }.addOnSuccessListener {
                _isEstudianteInscrito.value = true
                cargarDetalleTutoriaGrupal()
            }.addOnFailureListener {
                // Manejar error
            }
        }
    }

    fun desinscribirseDeTutoriaGrupal() {
        viewModelScope.launch {
            val estudianteId = auth.currentUser?.uid ?: return@launch
            val tutoriaRef = firestore.collection("tutorias_grupales").document(tutoriaId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(tutoriaRef)
                val tutoria = snapshot.toObject(TutoriaGrupal::class.java) ?: return@runTransaction

                if (!tutoria.estudiantesInscritos.contains(estudianteId)) {
                    // No está inscrito
                    return@runTransaction
                }

                tutoria.estudiantesInscritos.remove(estudianteId)
                transaction.update(tutoriaRef, "estudiantesInscritos", tutoria.estudiantesInscritos)
            }.addOnSuccessListener {
                _isEstudianteInscrito.value = false
                cargarDetalleTutoriaGrupal()
            }.addOnFailureListener {
                // Manejar error
            }
        }
    }
}

class DetalleTutoriaGrupalViewModelFactory(private val tutoriaId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleTutoriaGrupalViewModel::class.java)) {
            return DetalleTutoriaGrupalViewModel(tutoriaId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
