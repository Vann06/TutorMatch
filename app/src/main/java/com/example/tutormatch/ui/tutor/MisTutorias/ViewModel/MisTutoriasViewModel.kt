package com.example.tutormatch.ui.tutor.MisTutorias.ViewModel

import android.util.Log
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

    private val _misTutorias = MutableStateFlow<List<Any>>(emptyList())
    val misTutorias: StateFlow<List<Any>> = _misTutorias

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
                    // Cargar las tutorías solo si el tutorId no es nulo
                    val tutorias = obtenerTutorias(tutorId)
                    _misTutorias.value = tutorias
                    _estadoCarga.value = Result.success(Unit)
                } else {
                    // Si no hay un tutorId válido, se marca como error
                    _estadoCarga.value = Result.failure(Exception("Usuario no autenticado"))
                }
            } catch (e: Exception) {
                // Manejo de errores
                _estadoCarga.value = Result.failure(e)
                Log.e("MisTutorias", "Error al cargar las tutorías: ${e.message}")
            }
        }
    }

    private suspend fun obtenerTutorias(tutorId: String): List<Any> {
        val firestore = FirebaseFirestore.getInstance()
        return try {
            // Obtener todas las tutorías donde el tutorId sea igual al del tutor actual
            val snapshot = firestore.collection("tutorias")
                .whereEqualTo("tutorId", tutorId)
                .get()
                .await()

            // Mapear las tutorías dependiendo del tipo de tutoría
            snapshot.documents.mapNotNull { document ->
                when (document.getString("tipo")) {
                    "individual" -> {
                        // Filtrar solo las tutorías aceptadas
                        val tutoria = document.toObject(Tutoria1::class.java)
                        if (tutoria?.estado == "Aceptada") tutoria else null
                    }
                    "grupal" -> {
                        // Para las tutorías grupales simplemente se mapean
                        val tutoriaGrupal = document.toObject(TutoriaGrupal::class.java)
                        Log.d("MisTutorias", "Tutoría Grupal: $tutoriaGrupal")
                        tutoriaGrupal
                    }
                    else -> null
                }
            }
        } catch (e: Exception) {
            // En caso de error, retorna lista vacía y loguea el error
            Log.e("MisTutorias", "Error al obtener las tutorías: ${e.message}")
            emptyList()
        }
    }

    // Reiniciar el estado de carga para manejar el resultado
    fun resetEstadoCarga() {
        _estadoCarga.value = null
    }
}
