package com.example.tutormatch.ui.estudiante.SolicitudTutoria.view

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
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
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.ui.estudiante.SolicitudTutoria.SolicitudTutoriaViewModel
import com.example.tutormatch.ui.theme.AzulPrimario
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudTutoria(
    navHostController: NavHostController = rememberNavController(),
    tutor: Tutor1,  // Usando la estructura `Tutor1`
    viewModel: SolicitudTutoriaViewModel = viewModel()
) {
    // Referencias de estado del viewModel
    val selectedMateria by viewModel.selectedMateria.collectAsState()
    val selectedTipoTutoria by viewModel.selectedTipoTutoria.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTime by viewModel.selectedTime.collectAsState()
    val comment by viewModel.comment.collectAsState()

    // Estado para el Time Picker y Date Picker
    val timePickerDialogState = remember { mutableStateOf(false) }
    val datePickerDialogState = remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navHostController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(AzulPrimario),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "Agendar Tutoría",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = AzulPrimario,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

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
                    contentColor = AzulPrimario
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, AzulPrimario),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedMateria?.nombre ?: "Selecciona la materia")
            }
            DropdownMenu(
                expanded = viewModel.materiaDropdownExpanded,
                onDismissRequest = { viewModel.toggleMateriaDropdown() }
            ) {
                tutor.materias.forEach { materiaId ->
                    val materia = viewModel.getMateriaById(materiaId) // Función en ViewModel para obtener `Materia`
                    DropdownMenuItem(
                        text = { Text(materia?.nombre ?: "Materia desconocida") },
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
                color = Color.Black,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Button(
                onClick = { viewModel.toggleTipoTutoriaDropdown() },
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
                expanded = viewModel.tipoTutoriaDropdownExpanded,
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

        // Date Picker
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
                onValueChange = { viewModel.setSelectedDate(it) },
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

        // DatePicker Dialog
        if (datePickerDialogState.value) {
            val datePicker = android.app.DatePickerDialog(
                navHostController.context,
                { _, year, month, dayOfMonth ->
                    // Formatear la fecha
                    val selectedDateFormatted = "$dayOfMonth/${month + 1}/$year"
                    viewModel.setSelectedDate(selectedDateFormatted)
                    datePickerDialogState.value = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Time Picker
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
                    IconButton(onClick = {
                        timePickerDialogState.value = true
                    }) {
                        Icon(painter = painterResource(id = R.drawable.clock_icon), contentDescription = "Seleccionar hora")
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = AzulPrimario,
                    cursorColor = AzulPrimario
                )
            )
        }

        // TimePicker Dialog
        if (timePickerDialogState.value) {
            val timePicker = TimePickerDialog(
                navHostController.context,
                { _, hour, minute ->
                    viewModel.setSelectedTime("$hour:$minute")
                    timePickerDialogState.value = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePicker.show()
        }

        // Comment TextField
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Comentario",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            OutlinedTextField(
                value = comment,
                onValueChange = { viewModel.setComment(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Escribe tu comentario") },
                maxLines = 3,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = AzulPrimario,
                    cursorColor = AzulPrimario
                )
            )
        }

        // Submit Button
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // aqui va lo de solicitud de tutoria del viewmodel
                },
                colors = ButtonDefaults.buttonColors(AzulPrimario),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Enviar Solicitud", color = Color.White)
            }
        }
    }
}
