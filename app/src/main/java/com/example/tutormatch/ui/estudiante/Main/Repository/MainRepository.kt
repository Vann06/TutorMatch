package com.example.tutormatch.ui.estudiante.Main.Repository

import com.example.tutormatch.R
import com.example.tutormatch.estructuras.Estudiante
import com.example.tutormatch.estructuras.Materias
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.estructuras.Tutor1
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class EstudianteRepository(private val firestore: FirebaseFirestore) {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    suspend fun getMaterias(): MutableList<Materias> {
        _loading.value = true
        _errorMessage.value = null

        return try {
            val snapshot = firestore.collection("materias").get().await()
            snapshot.toObjects(Materias::class.java).toMutableList()
        } catch (e: Exception) {
            _errorMessage.value = "Error al obtener materias: ${e.message}"
            mutableListOf() // Retorna una lista vacía en caso de error
        } finally {
            _loading.value = false
        }
    }

    suspend fun getTutors(): MutableList<Tutor1> {
        _loading.value = true
        _errorMessage.value = null

        return try {
            val snapshot = firestore.collection("tutors").get().await()
            snapshot.toObjects(Tutor1::class.java).toMutableList()
        } catch (e: Exception) {
            _errorMessage.value = "Error al obtener tutores: ${e.message}"
            mutableListOf() // Retorna una lista vacía en caso de error
        } finally {
            _loading.value = false
        }
    }
}
