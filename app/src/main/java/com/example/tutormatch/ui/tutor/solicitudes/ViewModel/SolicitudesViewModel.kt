package com.example.tutormatch.ui.tutor.solicitudes.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SolicitudesViewModel : ViewModel() {

    private val _solicitudes = MutableStateFlow<List<Tutoria1>>(emptyList())
    val solicitudes: StateFlow<List<Tutoria1>> = _solicitudes

    init {
        cargarSolicitudes()
    }

    private fun cargarSolicitudes() {
        viewModelScope.launch {
            val tutorId = FirebaseAuth.getInstance().currentUser?.uid
            if (tutorId != null) {
                val solicitudesList = obtenerSolicitudes(tutorId)
                _solicitudes.value = solicitudesList
            }
        }
    }

    private suspend fun obtenerSolicitudes(tutorId: String): List<Tutoria1> {
        val firestore = FirebaseFirestore.getInstance()
        return try {
            val snapshot = firestore.collection("tutorias")
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("estado", "Pendiente")
                .get()
                .await()

            snapshot.toObjects(Tutoria1::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun aceptarSolicitud(tutoria: Tutoria1) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            try {
                firestore.collection("tutorias").document(tutoria.id)
                    .update("estado", "Aceptada")
                    .await()
                // Actualizar la lista de solicitudes
                cargarSolicitudes()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun rechazarSolicitud(tutoria: Tutoria1) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            try {
                firestore.collection("tutorias").document(tutoria.id)
                    .update("estado", "Rechazada")
                    .await()
                // Actualizar la lista de solicitudes
                cargarSolicitudes()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}
