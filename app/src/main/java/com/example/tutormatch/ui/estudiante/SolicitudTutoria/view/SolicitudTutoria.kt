package com.example.tutormatch.ui.estudiante.SolicitudTutoria.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.ui.estudiante.SolicitudTutoria.SolicitudTutoriaViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudTutoria(
    navController: NavHostController,
    tutorId: String,
    tutoriaId: String? = null,
    viewModel: SolicitudTutoriaViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Obtener el tutor por ID al iniciar
    LaunchedEffect(key1 = tutorId, key2 = tutoriaId) {
        viewModel.obtenerTutorPorId(tutorId)
        tutoriaId?.let { viewModel.cargarTutoriaExistente(it) }
    }

    // Referencias de estado del viewModel
    val selectedMateria by viewModel.selectedMateria.collectAsState()
    val selectedTipoTutoria by viewModel.selectedTipoTutoria.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTime by viewModel.selectedTime.collectAsState()
    val comment by viewModel.comment.collectAsState()
    val modalidadesTutor by viewModel.modalidadesTutor.collectAsState()
    val selectedModalidad by viewModel.selectedModalidad.collectAsState()

    // Obtener el contexto
    val context = LocalContext.current

    // Estado para el Time Picker y Date Picker
    var timePickerDialogState = remember { mutableStateOf(false) }
    var datePickerDialogState = remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    // Obtener el tutor
    val tutor by viewModel.tutor.collectAsState()

    val currentTutor = tutor

    val materiasTutor by viewModel.materiasTutor.collectAsState()

    // Observa el estado de creación de la tutoría
    val tutoriaCreationStatus by viewModel.tutoriaCreationStatus.collectAsState()

    // Contexto para mostrar Snackbars
    val scope = rememberCoroutineScope()

    LaunchedEffect(tutoriaCreationStatus) {
        tutoriaCreationStatus?.let { result ->
            if (result.isSuccess) {
                // Mostrar mensaje de éxito
                snackbarHostState.showSnackbar("Solicitud enviada exitosamente")
                // Navegar hacia atrás
                navController.popBackStack()
                // Reiniciar el estado
                viewModel.resetTutoriaCreationStatus()
            } else {
                // Mostrar mensaje de error
                snackbarHostState.showSnackbar("Error al enviar la solicitud: ${result.exceptionOrNull()?.message}")
                // Reiniciar el estado
                viewModel.resetTutoriaCreationStatus()
            }
        }
    }

    Scaffold(
        topBar = {
            AppBar("Solicitud Tutoría", navController)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { innerPadding ->
            if (currentTutor != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 16.dp)
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Materia dropdown
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Materia",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Button(
                            onClick = { viewModel.toggleMateriaDropdown() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color(0xFF3D44B6)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, Color(0xFF3D44B6)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = selectedMateria?.nombre ?: "Selecciona la materia")
                        }
                        DropdownMenu(
                            expanded = viewModel.materiaDropdownExpanded.value,
                            onDismissRequest = { viewModel.toggleMateriaDropdown() }
                        ) {
                            materiasTutor.forEach { materia ->
                                DropdownMenuItem(
                                    text = { Text(materia.nombre) },
                                    onClick = { viewModel.setSelectedMateria(materia) }
                                )
                            }
                        }
                    }

                    // Tipo de Tutoría dropdown
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tipo de Tutoría",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Button(
                            onClick = { viewModel.toggleTipoTutoriaDropdown() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color(0xFF3D44B6)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, Color(0xFF3D44B6)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = selectedTipoTutoria ?: "Selecciona el tipo de tutoría")
                        }
                        DropdownMenu(
                            expanded = viewModel.tipoTutoriaDropdownExpanded.value,
                            onDismissRequest = { viewModel.toggleTipoTutoriaDropdown() }
                        ) {
                            listOf("Desde 0", "Intermedio", "Avanzado").forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo) },
                                    onClick = { viewModel.setSelectedTipoTutoria(tipo) }
                                )
                            }
                        }
                    }
                    // Modalidad dropdown
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Modalidad",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Button(
                            onClick = { viewModel.toggleModalidadDropdown() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color(0xFF3D44B6)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, Color(0xFF3D44B6)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = selectedModalidad ?: "Selecciona la modalidad")
                        }
                        DropdownMenu(
                            expanded = viewModel.modalidadDropdownExpanded.value,
                            onDismissRequest = { viewModel.toggleModalidadDropdown() }
                        ) {
                            modalidadesTutor.forEach { modalidad ->
                                DropdownMenuItem(
                                    text = { Text(modalidad) },
                                    onClick = { viewModel.setSelectedModalidad(modalidad) }
                                )
                            }
                        }
                    }


                    // Date Picker
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Fecha de la tutoría",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        OutlinedTextField(
                            value = selectedDate,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Selecciona una fecha") },
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialogState.value = true }) {
                                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Seleccionar fecha")
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF3D44B6),
                                cursorColor = Color(0xFF1976D2)
                            )
                        )
                    }

                    // DatePicker Dialog
                    if (datePickerDialogState.value) {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                val selectedDateFormatted = "$dayOfMonth/${month + 1}/$year"
                                viewModel.setSelectedDate(selectedDateFormatted)
                                datePickerDialogState.value = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }

                    // Time Picker
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Hora de la tutoría",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        OutlinedTextField(
                            value = selectedTime,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(text = "Selecciona una hora") },
                            trailingIcon = {
                                IconButton(onClick = { timePickerDialogState.value = true }) {
                                    Icon(imageVector = Icons.Filled.Schedule, contentDescription = "Seleccionar hora")
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF3D44B6),
                                cursorColor = Color(0xFF3D44B6)
                            )
                        )
                    }

                    // TimePicker Dialog
                    if (timePickerDialogState.value) {
                        TimePickerDialog(
                            context,
                            { _, hour, minute ->
                                val selectedTimeFormatted = String.format("%02d:%02d", hour, minute)
                                viewModel.setSelectedTime(selectedTimeFormatted)
                                timePickerDialogState.value = false
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                        ).show()
                    }
                    // Comment TextField
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Comentario",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        OutlinedTextField(
                            value = comment,
                            onValueChange = { viewModel.setComment(it) },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Escribe tu comentario") },
                            maxLines = 3,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color(0xFF3D44B6),
                                cursorColor = Color(0xFF1976D2)
                            )
                        )
                    }

                    // Submit Button
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                scope.launch {
                                    // Obtener el ID del estudiante desde FirebaseAuth
                                    val estudianteId = FirebaseAuth.getInstance().currentUser?.uid
                                    if (estudianteId != null) {
                                        viewModel.createTutoria(estudianteId, tutorId)
                                    } else {
                                        snackbarHostState.showSnackbar("Usuario no autenticado")
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xFF3D44B6)),
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text("Enviar Solicitud", color = Color.White)
                        }
                    }
                }
            } else {
                // Mostrar indicador de carga
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF3D44B6))
                }
            }
        }
    )
}
