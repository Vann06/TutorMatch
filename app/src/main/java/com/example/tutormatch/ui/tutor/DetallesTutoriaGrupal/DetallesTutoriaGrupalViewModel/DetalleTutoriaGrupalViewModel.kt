package com.example.tutormatch.ui.tutor.DetallesTutoriaGrupal.DetallesTutoriaGrupalViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetalleTutoriaGrupalTutorViewModel(private val tutoriaId: String) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val _tutoriaGrupal = MutableStateFlow<TutoriaGrupal?>(null)
    val tutoriaGrupal: StateFlow<TutoriaGrupal?> = _tutoriaGrupal

    private val _estudiantes = MutableStateFlow<List<Estudiante1>>(emptyList())
    val estudiantes: StateFlow<List<Estudiante1>> = _estudiantes

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
                    // Obtener la lista de estudiantes inscritos
                    val estudiantesIds = it.estudiantesInscritos
                    val estudiantesList = mutableListOf<Estudiante1>()

                    for (estudianteId in estudiantesIds) {
                        val estudianteSnapshot = firestore.collection("estudiantes").document(estudianteId).get().await()
                        val estudiante = estudianteSnapshot.toObject(Estudiante1::class.java)
                        if (estudiante != null) {
                            estudiantesList.add(estudiante)
                        }
                    }

                    _estudiantes.value = estudiantesList
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun cancelarTutoriaGrupal(navController: NavHostController) {
        viewModelScope.launch {
            try {
                firestore.collection("tutorias_grupales").document(tutoriaId)
                    .update("estado", "Cancelada")
                    .await()
                navController.popBackStack()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}

class DetalleTutoriaGrupalTutorViewModelFactory(private val tutoriaId: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleTutoriaGrupalTutorViewModel::class.java)) {
            return DetalleTutoriaGrupalTutorViewModel(tutoriaId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
