package com.example.tutormatch.ui.tutor.MisTutorias.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
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

    init {
        cargarMisTutorias()
    }

    private fun cargarMisTutorias() {
        viewModelScope.launch {
            val tutorId = FirebaseAuth.getInstance().currentUser?.uid
            if (tutorId != null) {
                val tutorias = obtenerTutoriasDelTutor(tutorId)
                _misTutorias.value = tutorias
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
            emptyList()
        }
    }
}
