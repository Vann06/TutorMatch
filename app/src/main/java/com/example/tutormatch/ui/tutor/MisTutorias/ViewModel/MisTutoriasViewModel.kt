package com.example.tutormatch.ui.tutor.MisTutorias.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MisTutoriasViewModel : ViewModel() {

    private val _misTutoriasIndividuales = MutableStateFlow<List<Tutoria1>>(emptyList())
    val misTutoriasIndividuales: StateFlow<List<Tutoria1>> = _misTutoriasIndividuales

    private val _misTutoriasGrupales = MutableStateFlow<List<TutoriaGrupal>>(emptyList())
    val misTutoriasGrupales: StateFlow<List<TutoriaGrupal>> = _misTutoriasGrupales

    private val _estadoCarga = MutableStateFlow<Result<Unit>?>(null)
    val estadoCarga: StateFlow<Result<Unit>?> = _estadoCarga

    init {
        cargarMisTutorias()
    }

    fun cargarMisTutorias() {
        viewModelScope.launch {
            try {
                val tutorId = FirebaseAuth.getInstance().currentUser?.uid
                if (tutorId != null) {
                    val tutoriasIndividuales = obtenerTutoriasIndividualesDelTutor(tutorId)
                    val tutoriasGrupales = obtenerTutoriasGrupalesDelTutor(tutorId)
                    _misTutoriasIndividuales.value = tutoriasIndividuales
                    _misTutoriasGrupales.value = tutoriasGrupales
                    // ... (Actualizar estado de carga)
                } else {
                    // ... (Manejar error de autenticación)
                }
            } catch (e: Exception) {
                // ... (Manejar excepciones)
            }
        }
    }
    private suspend fun obtenerTutoriasIndividualesDelTutor(tutorId: String): List<Tutoria1> {
        val firestore = FirebaseFirestore.getInstance()
        return try {
            val snapshot = firestore.collection("tutorias_individuales") // Asegúrate de usar 'tutorias_individuales'
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("estado", "Aceptada")
                .get()
                .await()

            snapshot.toObjects(Tutoria1::class.java)
        } catch (e: Exception) {
            emptyList() // Retorna lista vacía en caso de error
        }
    }


    private suspend fun obtenerTutoriasGrupalesDelTutor(tutorId: String): List<TutoriaGrupal> {
        val firestore = FirebaseFirestore.getInstance()
        return try {
            val snapshot = firestore.collection("tutorias_grupales")
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("estado", "Disponible")
                .get()
                .await()

            snapshot.toObjects(TutoriaGrupal::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Reiniciar estado de carga para manejar el resultado
    fun resetEstadoCarga() {
        _estadoCarga.value = null
    }
}
