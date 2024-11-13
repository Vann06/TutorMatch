package com.example.tutormatch.ui.estudiante.SolicitudTutoria

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class SolicitudTutoriaViewModel(
    private val repository: SolicitudTutoriaRepository = SolicitudTutoriaRepository()
) : ViewModel() {

    // *** Variables de Estado ***
    private val _tutor = MutableStateFlow<Tutor1?>(null)
    val tutor: StateFlow<Tutor1?> = _tutor

    private val _materiasTutor = MutableStateFlow<List<Materia>>(emptyList())
    val materiasTutor: StateFlow<List<Materia>> = _materiasTutor

    private val _modalidadesTutor = MutableStateFlow<List<String>>(emptyList())
    val modalidadesTutor: StateFlow<List<String>> = _modalidadesTutor

    private val _selectedMateria = MutableStateFlow<Materia?>(null)
    val selectedMateria: StateFlow<Materia?> = _selectedMateria

    private val _selectedModalidad = MutableStateFlow<String?>(null)
    val selectedModalidad: StateFlow<String?> = _selectedModalidad

    private val _selectedTipoTutoria = MutableStateFlow<String?>(null)
    val selectedTipoTutoria: StateFlow<String?> = _selectedTipoTutoria

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _selectedTime = MutableStateFlow("")
    val selectedTime: StateFlow<String> = _selectedTime

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _tutoriaCreationStatus = MutableStateFlow<Result<Unit>?>(null)
    val tutoriaCreationStatus: StateFlow<Result<Unit>?> = _tutoriaCreationStatus

    var materiaDropdownExpanded = mutableStateOf(false)
        private set

    var modalidadDropdownExpanded = mutableStateOf(false)
        private set

    var tipoTutoriaDropdownExpanded = mutableStateOf(false)
        private set

    // *** Métodos de Configuración ***

    // Obtener el tutor por ID y sus materias y modalidades
    fun obtenerTutorPorId(tutorId: String) {
        viewModelScope.launch {
            val result = repository.obtenerTutorPorId(tutorId)
            if (result.isSuccess) {
                val tutor = result.getOrNull()
                _tutor.value = tutor

                tutor?.let {
                    // Procesar materias
                    val materiasList = it.materias.map { materiaNombre ->
                        Materia(id = materiaNombre, nombre = materiaNombre)
                    }
                    _materiasTutor.value = materiasList

                    // Procesar modalidades
                    val modalidadesList = it.modalidad.split(",").map { modalidad ->
                        modalidad.trim()
                    }
                    _modalidadesTutor.value = modalidadesList
                }
            } else {
                // Manejar error al obtener el tutor
                println("Error al obtener el tutor: ${result.exceptionOrNull()?.message}")
            }
        }
    }


    fun toggleMateriaDropdown() {
        materiaDropdownExpanded.value = !materiaDropdownExpanded.value
    }
    fun toggleModalidadDropdown() {
        modalidadDropdownExpanded.value = !modalidadDropdownExpanded.value
    }

    fun setSelectedMateria(materia: Materia?) {
        _selectedMateria.value = materia
        toggleMateriaDropdown()
    }

    fun toggleTipoTutoriaDropdown() {
        tipoTutoriaDropdownExpanded.value = !tipoTutoriaDropdownExpanded.value
    }

    fun setSelectedTipoTutoria(tipo: String) {
        _selectedTipoTutoria.value = tipo
        toggleTipoTutoriaDropdown()
    }
    fun setSelectedModalidad(modalidad: String) {
        _selectedModalidad.value = modalidad
        toggleModalidadDropdown()
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun setSelectedTime(time: String) {
        _selectedTime.value = time
    }

    fun setComment(comment: String) {
        _comment.value = comment
    }

    // *** Métodos para Crear la Tutoría ***
    fun createTutoria(estudianteId: String, tutorId: String) {
        val materiaId = selectedMateria.value?.id ?: run {
            // Manejar el caso de materia no seleccionada
            _tutoriaCreationStatus.value = Result.failure(Exception("Debe seleccionar una materia"))
            return
        }
        val modalidadSeleccionada = selectedModalidad.value ?: run {
            // Manejar el caso de modalidad no seleccionada
            _tutoriaCreationStatus.value = Result.failure(Exception("Debe seleccionar una modalidad"))
            return
        }
        val tutoria = Tutoria1(
            id = UUID.randomUUID().toString(), // ID único
            estudianteId = estudianteId,
            tutorId = tutorId,
            materiaId = materiaId,
            fecha = selectedDate.value,
            hora = selectedTime.value,
            modalidad = modalidadSeleccionada, // Usar la modalidad seleccionada
            mensaje = comment.value,
            estado = "Pendiente" // Estado inicial
        )

        viewModelScope.launch {
            val result = repository.createTutoria(tutoria)
            _tutoriaCreationStatus.value = result
        }
    }
    // Agregar el método para cargar la tutoría existente
    fun cargarTutoriaExistente(tutoriaId: String) {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            val tutoriaSnapshot = firestore.collection("tutorias").document(tutoriaId).get().await()
            val tutoria = tutoriaSnapshot.toObject(Tutoria1::class.java)

            tutoria?.let {
                // Setear los valores de la tutoría en los StateFlows
                _selectedMateria.value = Materia(id = it.materiaId, nombre = it.materiaId) // Ajusta si tienes nombres diferentes
                _selectedModalidad.value = it.modalidad
                _selectedTipoTutoria.value = it.mensaje // Aquí usa el campo que corresponda
                _selectedDate.value = it.fecha
                _selectedTime.value = it.hora
                _comment.value = it.mensaje
            }
        }
    }

    fun resetTutoriaCreationStatus() {
        _tutoriaCreationStatus.value = null
    }


}
