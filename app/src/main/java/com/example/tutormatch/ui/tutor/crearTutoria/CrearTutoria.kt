package com.example.tutormatch.ui.tutor.crearTutoria

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tutormatch.R
import com.example.tutormatch.ui.theme.AzulPrimario
import com.example.tutormatch.estructuras.Materias
import com.example.tutormatch.estructuras.Tutor


@Preview(showBackground = true)
@Composable
fun PrevieCreacionTutoria() {
    CreacionTutoria(
        navHostController = rememberNavController(),
        tutor = Tutor(
            id = "1",
            nombre = "Tutor Ejemplo",
            usuario = "usuario",
            contraseña = "contraseña",
            myStudents = mutableListOf(),
            fotoPerfil = R.drawable.ic_launcher_background,
            materias = mutableListOf(
                Materias(nombre = "Matemáticas"),
                Materias(nombre = "Física"),
                Materias(nombre = "Química")
            ),
            descripcion = "Descripción",
            modalidad = "Online"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CreacionTutoria(
    navHostController: NavHostController = rememberNavController(),
    tutor: Tutor
) {
    var expandedMateria by remember { mutableStateOf(false) }
    var selectedMateria by remember { mutableStateOf<Materias?>(null) }
    var expandedTipoTutoria by remember { mutableStateOf(false) }
    var selectedTipoTutoria by remember { mutableStateOf<String?>(null) }
    var comment by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") } // Date placeholder
    var selectedTime by remember { mutableStateOf("") } // Time placeholder

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
                onClick = { expandedMateria = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = AzulPrimario
                ),
                shape = RoundedCornerShape(16.dp), // Curved corners
                border = BorderStroke(1.dp, AzulPrimario), // Border to define button shape
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = selectedMateria?.nombre ?: "Selecciona la materia")
            }

            DropdownMenu(
                expanded = expandedMateria,
                onDismissRequest = { expandedMateria = false }
            ) {
                tutor.materias.forEach { materia ->
                    DropdownMenuItem(
                        text = { Text(materia.nombre) },
                        onClick = {
                            selectedMateria = materia
                            expandedMateria = false
                        }
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
                onClick = { expandedTipoTutoria = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = AzulPrimario
                ),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, AzulPrimario),
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(0.01.dp)),

                ) {
                Text(text = selectedTipoTutoria ?: "Selecciona el tipo de tutoría")
            }

            DropdownMenu(
                expanded = expandedTipoTutoria,
                onDismissRequest = { expandedTipoTutoria = false }
            ) {
                listOf("Desde 0", "Intermedio", "Avanzado").forEach { tipo ->
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
                onValueChange = { selectedDate = it },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(text = "Selecciona una fecha") },
                trailingIcon = {
                    IconButton(onClick = { /* Add date picker logic here */ }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Seleccionar fecha"
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = AzulPrimario,
                    cursorColor = AzulPrimario
                )
            )
        }

        // Time Picker (OutlinedTextField with Clock Icon)
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
                onValueChange = { selectedTime = it },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(text = "Selecciona una hora") },
                trailingIcon = {
                    IconButton(onClick = { /* Add time picker logic here */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.clock_icon) ,
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
                text = "Escribe una descripción de que tratará tu clase: ",
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
                Text(text = "Crear Tutoría", color = Color.White)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    var date by remember { mutableStateOf(value = "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Calendar View") },
            )
        },
        content = { paddingValues: PaddingValues -> // Se utiliza el paddingValues
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues), // Se aplica el padding
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Aquí se añade el AndroidView
                AndroidView(factory = { CalendarView(it) }, update = { calendarView: CalendarView ->
                    calendarView.setOnDateChangeListener { _, year, month, day ->
                        date = "$day - ${month + 1} - $year"
                    }
                })
                Text(text = date)
            }
        }
    )
}
