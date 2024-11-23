package com.example.tutormatch.ui.tutor.crearTutoria.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.example.tutormatch.ui.tutor.crearTutoria.Repository.CrearTutoriaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CrearTutoriaViewModel(
    private val repository: CrearTutoriaRepository = CrearTutoriaRepository()
) : ViewModel() {

    // Variables de estado para la creación de tutorías grupales
    private val _materiasDisponibles = MutableStateFlow<List<Materia>>(emptyList())
    val materiasDisponibles: StateFlow<List<Materia>> = _materiasDisponibles

    private val _selectedMateria = MutableStateFlow<Materia?>(null)
    val selectedMateria: StateFlow<Materia?> = _selectedMateria

    private val _modalidadesTutor = MutableStateFlow<List<String>>(emptyList())
    val modalidadesTutor: StateFlow<List<String>> = _modalidadesTutor

    private val _selectedModalidad = MutableStateFlow<String?>(null)
    val selectedModalidad: StateFlow<String?> = _selectedModalidad

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _selectedTime = MutableStateFlow("")
    val selectedTime: StateFlow<String> = _selectedTime

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _tutoriaCreationStatus = MutableStateFlow<Result<String>?>(null)
    val tutoriaCreationStatus: StateFlow<Result<String>?> = _tutoriaCreationStatus

    var materiaDropdownExpanded = mutableStateOf(false)
        private set

    var modalidadDropdownExpanded = mutableStateOf(false)
        private set

    // Inicialización y carga de datos
    init {
        cargarMateriasDisponibles()
        cargarModalidadesTutor()
    }

    private fun cargarMateriasDisponibles() {
        viewModelScope.launch {
            val tutorId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val tutor = repository.obtenerTutorPorId(tutorId)
            if (tutor.isSuccess) {
                tutor.getOrNull()?.let {
                    val materiasList = it.materias.map { materiaId ->
                        Materia(id = materiaId, nombre = materiaId) // Suponiendo que cada materia tiene un ID y nombre igual
                    }
                    _materiasDisponibles.value = materiasList
                }
            } else {
                // Manejar error si no se puede obtener el tutor
                println("Error al obtener el tutor: ${tutor.exceptionOrNull()?.message}")
            }
        }
    }


    private fun cargarModalidadesTutor() {
        viewModelScope.launch {
            val tutorId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val tutor = repository.obtenerTutorPorId(tutorId)
            if (tutor.isSuccess) {
                tutor.getOrNull()?.let {
                    val modalidadesList = it.modalidad.split(",").map { modalidad -> modalidad.trim() }
                    _modalidadesTutor.value = modalidadesList
                }
            }
        }
    }

    // Métodos para configurar campos seleccionados
    fun toggleMateriaDropdown() {
        materiaDropdownExpanded.value = !materiaDropdownExpanded.value
    }

    fun setSelectedMateria(materia: Materia?) {
        _selectedMateria.value = materia
        toggleMateriaDropdown()
    }

    fun toggleModalidadDropdown() {
        modalidadDropdownExpanded.value = !modalidadDropdownExpanded.value
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

    // Método para crear la tutoría grupal
    fun crearTutoriaGrupal() {
        val tutorId = FirebaseAuth.getInstance().currentUser?.uid ?: run {
            _tutoriaCreationStatus.value = Result.failure(Exception("Tutor no autenticado"))
            return
        }

        val materiaId = selectedMateria.value?.id ?: run {
            _tutoriaCreationStatus.value = Result.failure(Exception("Debe seleccionar una materia"))
            return
        }

        val modalidadSeleccionada = selectedModalidad.value ?: run {
            _tutoriaCreationStatus.value = Result.failure(Exception("Debe seleccionar una modalidad"))
            return
        }

        val nuevaTutoriaGrupal = TutoriaGrupal(
            id = "", // El ID asignado por Firestore
            tutorId = tutorId,
            materiaId = materiaId,
            fecha = selectedDate.value,
            hora = selectedTime.value,
            modalidad = modalidadSeleccionada,
            mensaje = comment.value,
            cuposMaximos = 15,
            estudiantesInscritos = mutableListOf()
        )

        viewModelScope.launch {
            val resultado = repository.crearTutoriaGrupal(nuevaTutoriaGrupal)
            _tutoriaCreationStatus.value = resultado
        }
    }

    // Reiniciar estado
    fun resetTutoriaCreationStatus() {
        _tutoriaCreationStatus.value = null
    }
}
