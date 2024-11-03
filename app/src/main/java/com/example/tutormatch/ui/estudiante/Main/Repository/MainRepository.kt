package com.example.tutormatch.ui.estudiante.Main.Repository

import android.util.Log
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class EstudianteRepository(private val firestore: FirebaseFirestore) {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    suspend fun getMaterias(): MutableList<Materia> {
        _loading.value = true
        _errorMessage.value = null

        return try {
            val snapshot = firestore.collection("materias").get().await()
            snapshot.toObjects(Materia::class.java).toMutableList()
        } catch (e: Exception) {
            _errorMessage.value = "Error al obtener materias: ${e.message}"
            mutableListOf()
        } finally {
            _loading.value = false
        }
    }

    suspend fun getTutors(): MutableList<Tutor1> {
        _loading.value = true
        _errorMessage.value = null

        return try {
            val snapshot = firestore.collection("tutores").get().await()
            val tutorsList = snapshot.toObjects(Tutor1::class.java).toMutableList()
            Log.d("EstudianteRepository", "Tutors fetched: ${tutorsList.size}")
            tutorsList
        } catch (e: Exception) {
            _errorMessage.value = "Error al obtener tutores: ${e.message}"
            Log.e("EstudianteRepository", "Error fetching tutors", e)
            mutableListOf()
        } finally {
            _loading.value = false
        }
    }

}

