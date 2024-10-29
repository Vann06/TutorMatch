package com.example.tutormatch.ui.estudiante.SolicitudTutoria.view

import android.widget.CalendarView
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.Materia
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.ui.estudiante.SolicitudTutoria.SolicitudTutoriaViewModel

import com.example.tutormatch.ui.theme.AzulPrimario


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudTutoria(
    navHostController: NavHostController = rememberNavController(),
    tutor: Tutor,
    viewModel: SolicitudTutoriaViewModel = viewModel() // Asegúrate de que el ViewModel está importado correctamente
) {
    // Referencias de estado del viewModel
    val selectedMateria by viewModel.selectedMateria.collectAsState()
    val selectedTipoTutoria by viewModel.selectedTipoTutoria.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTime by viewModel.selectedTime.collectAsState()
    val comment by viewModel.comment.collectAsState()

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
                tutor.materias.forEach { materia ->
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
                    IconButton(onClick = { /* Date picker */ }) {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Seleccionar fecha")
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = AzulPrimario,
                    cursorColor = AzulPrimario
                )
            )
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
                onValueChange = { viewModel.setSelectedTime(it) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Selecciona una hora") },
                trailingIcon = {
                    IconButton(onClick = { /* Time picker */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.clock_icon),
                            contentDescription = "Seleccionar hora",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = AzulPrimario,
                    cursorColor = AzulPrimario
                )
            )
        }

        // Comment TextField
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Escribe lo que quieras estudiar",
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
                    cursorColor = AzulPrimario
                )
            )
        }

        // Submit button
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navHostController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(AzulPrimario)
            ) {
                Text(text = "Solicitar Tutoría", color = Color.White)
            }
        }
    }
}
