package com.example.tutormatch.ui.tutor.crearTutoria.View

import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.ui.tutor.crearTutoria.viewmodel.CrearTutoriaViewModel
import com.example.tutormatch.ui.theme.AzulPrimario
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreacionTutoria(
    navController: NavHostController,
    viewModel: CrearTutoriaViewModel = viewModel()
) {
    val materias = viewModel.materiasDisponibles.collectAsState().value
    val selectedMateria = viewModel.selectedMateria.collectAsState().value
    val selectedModalidad = viewModel.selectedModalidad.collectAsState().value
    val selectedDate = viewModel.selectedDate.collectAsState().value
    val selectedTime = viewModel.selectedTime.collectAsState().value
    val comment = viewModel.comment.collectAsState().value
    val tutoriaCreationStatus = viewModel.tutoriaCreationStatus.collectAsState().value

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var datePickerDialogState = remember { mutableStateOf(false) }
    var timePickerDialogState = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppBar(title = "Crear Tutoría Grupal", navController = navController)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.Start
            ) {
                // Materia dropdown
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Materia",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
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
                        materias.forEach { materia ->
                            DropdownMenuItem(
                                text = { Text(materia.nombre) },
                                onClick = { viewModel.setSelectedMateria(materia) }
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
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Button(
                        onClick = { viewModel.toggleModalidadDropdown() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFF3D44B6)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFF3D44B6)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = selectedModalidad ?: "Selecciona la modalidad")
                    }

                    DropdownMenu(
                        expanded = viewModel.modalidadDropdownExpanded.value,
                        onDismissRequest = { viewModel.toggleModalidadDropdown() }
                    ) {
                        viewModel.modalidadesTutor.collectAsState().value.forEach { modalidad ->
                            DropdownMenuItem(
                                text = { Text(modalidad) },
                                onClick = { viewModel.setSelectedModalidad(modalidad) }
                            )
                        }
                    }
                }

                // Fecha de la tutoría
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Fecha de la tutoría",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
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
                            cursorColor = Color(0xFF3D44B6)
                        )
                    )
                }

                if (datePickerDialogState.value) {
                    android.app.DatePickerDialog(
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

                // Hora de la tutoría
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Hora de la tutoría",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
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

                // Descripción
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Descripción",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    TextField(
                        value = comment,
                        onValueChange = { viewModel.setComment(it) },
                        placeholder = { Text(text = "Escribe aquí...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF0F0F0),
                            cursorColor = Color(0xFF3D44B6)
                        )
                    )
                }

                // Botón de Crear Tutoría Grupal
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            viewModel.crearTutoriaGrupal()
                            //navController.navigate("home")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF3D44B6))
                    ) {
                        Text(text = "Crear Tutoría Grupal", color = Color.White)
                    }
                }
            }
        }
    )
}

