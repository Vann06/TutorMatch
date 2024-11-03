package com.example.tutormatch.ui.estudiante.SolicitudTutoria.view

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
                // Iterar sobre los IDs de materias del tutor
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

        // Time Picker, Comment TextField, and Submit button (without further modification)

    }
}
