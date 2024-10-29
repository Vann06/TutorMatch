package com.example.tutormatch.ui.estudiante.SolicitudTutoria

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutormatch.estructuras.Materia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SolicitudTutoriaViewModel : ViewModel() {

    // Estados para las variables
    private val _selectedMateria = MutableStateFlow<Materia?>(null)
    val selectedMateria: StateFlow<Materia?> = _selectedMateria

    private val _selectedTipoTutoria = MutableStateFlow<String?>(null)
    val selectedTipoTutoria: StateFlow<String?> = _selectedTipoTutoria

    private val _selectedDate = MutableStateFlow("")
    val selectedDate: StateFlow<String> = _selectedDate

    private val _selectedTime = MutableStateFlow("")
    val selectedTime: StateFlow<String> = _selectedTime

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    // Dropdown para Materia
    var materiaDropdownExpanded by mutableStateOf(false)
        private set

    fun toggleMateriaDropdown() {
        materiaDropdownExpanded = !materiaDropdownExpanded
    }

    fun setSelectedMateria(materia: Materia) {
        _selectedMateria.value = materia
        toggleMateriaDropdown() // Cierra el dropdown después de seleccionar
    }

    // Dropdown para Tipo de Tutoría
    var tipoTutoriaDropdownExpanded by mutableStateOf(false)
        private set

    fun toggleTipoTutoriaDropdown() {
        tipoTutoriaDropdownExpanded = !tipoTutoriaDropdownExpanded
    }

    fun setSelectedTipoTutoria(tipo: String) {
        _selectedTipoTutoria.value = tipo
        toggleTipoTutoriaDropdown() // Cierra el dropdown después de seleccionar
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
}
