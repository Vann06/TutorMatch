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
    var expandedMateria by remember { mutableStateOf(false) }
    var selectedMateria by remember { mutableStateOf<Materia?>(null) }
    var expandedTipoTutoria by remember { mutableStateOf(false) }
    var selectedTipoTutoria by remember { mutableStateOf<String?>(null) }
    var comment by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var esGrupal by remember { mutableStateOf(false) }

    // Obtiene los datos de materias y tipos de tutoría desde el ViewModel
    val materias = viewModel.materiasDisponibles.collectAsState().value
    val tiposDeTutoria = viewModel.tiposDeTutoria.collectAsState().value

    val context = LocalContext.current

    val materiasTutor by viewModel.materiasTutor.collectAsState()

    var timePickerDialogState = remember { mutableStateOf(false) }
    var datePickerDialogState = remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    Scaffold(
        topBar = {
            AppBar(title = "Crear Tutoría", navController = navController)
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
                        materiasTutor.forEach { materia ->
                            DropdownMenuItem(
                                text = { Text(materia.nombre) },
                                onClick = { viewModel.setSelectedMateria(materia) }
                            )
                        }
                    }
                    //Text(text = "Materias cargadas: ${materiasTutor.size}")

                }

                // Tipo de Tutoría dropdown
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Tipo de Tutoría",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Button(
                        onClick = { expandedTipoTutoria = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = AzulPrimario
                        ),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, AzulPrimario),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = selectedTipoTutoria ?: "Selecciona el tipo de tutoría")
                    }

                    DropdownMenu(
                        expanded = expandedTipoTutoria,
                        onDismissRequest = { expandedTipoTutoria = false }
                    ) {
                        tiposDeTutoria.forEach { tipo ->
                            DropdownMenuItem(
                                text = { Text(tipo) },
                                onClick = {
                                    selectedTipoTutoria = tipo
                                    expandedTipoTutoria = false
                                }
                            )
                        }
                    }
                }

                // Fecha y Hora
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
                            focusedBorderColor = AzulPrimario,
                            cursorColor = AzulPrimario
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
                            focusedBorderColor = AzulPrimario,
                            cursorColor = AzulPrimario
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
                        text = "Escribe una descripción de qué tratará tu clase:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    TextField(
                        value = comment,
                        onValueChange = { comment = it },
                        placeholder = { Text(text = "Escribe aquí...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xFFF0F0F0),
                            cursorColor = AzulPrimario
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "¿Tutoría Grupal?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = esGrupal,
                            onCheckedChange = { esGrupal = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = AzulPrimario
                            )
                        )
                    }
                }

                // Botón de Crear Tutoría
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Lógica para crear la tutoría
                            viewModel.crearTutoria(
                                materia = selectedMateria,
                                tipoTutoria = selectedTipoTutoria,
                                fecha = selectedDate,
                                hora = selectedTime,
                                descripcion = comment,
                                esGrupal = esGrupal // Pasamos el nuevo campo
                            )
                            // Navegar a la pantalla deseada después de crear la tutoría
                            navController.navigate("home")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(AzulPrimario)
                    ) {
                        Text(text = "Crear Tutoría", color = Color.White)
                    }
                }
            }
        }
    )
}

