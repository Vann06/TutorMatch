package com.example.tutormatch.ui.tutor.MisTutorias.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MisTutoriasViewModel : ViewModel() {

    private val _misTutorias = MutableStateFlow<List<Tutoria1>>(emptyList())
    val misTutorias: StateFlow<List<Tutoria1>> = _misTutorias

    private val _estadoCarga = MutableStateFlow<Result<Unit>?>(null)
    val estadoCarga: StateFlow<Result<Unit>?> = _estadoCarga

    init {
        cargarMisTutorias()
    }

    private fun cargarMisTutorias() {
        viewModelScope.launch {
            try {
                val tutorId = FirebaseAuth.getInstance().currentUser?.uid
                if (tutorId != null) {
                    val tutorias = obtenerTutoriasDelTutor(tutorId)
                    _misTutorias.value = tutorias
                    _estadoCarga.value = Result.success(Unit)
                } else {
                    _estadoCarga.value = Result.failure(Exception("Usuario no autenticado"))
                }
            } catch (e: Exception) {
                _estadoCarga.value = Result.failure(e)
            }
        }
    }

    private suspend fun obtenerTutoriasDelTutor(tutorId: String): List<Tutoria1> {
        val firestore = FirebaseFirestore.getInstance()
        return try {
            val snapshot = firestore.collection("tutorias")
                .whereEqualTo("tutorId", tutorId)
                .get()
                .await()

            snapshot.toObjects(Tutoria1::class.java)
        } catch (e: Exception) {
            emptyList() // Retorna lista vacía en caso de error
        }
    }

    // Reiniciar estado de carga para manejar el resultado
    fun resetEstadoCarga() {
        _estadoCarga.value = null
    }
}
