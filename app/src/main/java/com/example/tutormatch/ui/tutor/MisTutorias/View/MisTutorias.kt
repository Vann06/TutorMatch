package com.example.tutormatch.ui.tutor.MisTutorias.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.Estudiante
import com.example.tutormatch.estructuras.Materia
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.estructuras.Tutoria
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.tutor.MisTutorias.ViewModel.MisTutoriasViewModel

@Composable
fun MisTutorias(navController: NavController, infoEstudiante: Estudiante) {
    // Obtener instancia del ViewModel
    val viewModel: MisTutoriasViewModel = viewModel()

    // Recoger el estado de mis tutorías
    val misTutorias by viewModel.misTutorias.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(misTutorias) { tutoria ->
            TutoriaCard(navController = navController, infotutoria = tutoria)
        }
    }
}

@Composable
fun TutoriaCard(navController: NavController, infotutoria: Tutoria) {
    val nombreTutor = infotutoria.tutor?.nombre

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(NavigationState.MisTutorias) // Cambiar a un destino específico si es necesario
            })
            .padding(8.dp)
            .shadow(12.dp, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono del tutor
            Image(
                painter = painterResource(id = R.drawable.estudiante),
                contentDescription = "Imagen de perfil de alumno",
                modifier = Modifier
                    .padding(6.dp)
                    .size(50.dp) // Tamaño más pequeño como en la imagen
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape) // Borde alrededor del icono
            )

            // Columna con nombre del tutor y modalidad
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                if (nombreTutor != null) {
                    Text(
                        text = nombreTutor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp, // Tamaño de texto más pequeño
                        color = Color.White // Texto en blanco
                    )
                }
                Text(
                    text = "Modalidad: " + infotutoria.modalidad,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.LightGray // Color más claro
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Materia con Card circular
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Blue // Fondo azul (cambia a tu color deseado)
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .wrapContentSize() // Tamaño compacto
                ) {
                    Text(
                        text = infotutoria.materia.nombre,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            // Columna con fecha y hora
            Column(
                horizontalAlignment = Alignment.End // Alineación a la derecha
            ) {
                Text(
                    text = "Fecha: " + infotutoria.fecha,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = Color.LightGray // Color más claro
                )
                Text(
                    text = "Hora: " + infotutoria.hora,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTutoriaCard() {
    // Crear datos de ejemplo para la tutoría
    val tutorEjemplo = Tutor(
        id = "1",
        nombre = "Alumno Ejemplo",
        usuario = "usuario",
        contraseña = "contraseña",
        myStudents = mutableListOf(),
        fotoPerfil = R.drawable.estudiante,
        materias = mutableListOf(
            Materia(nombre = "Física II")
        ),
        descripcion = "Descripción",
        modalidad = "Online"
    )

    // Crear un objeto Tutoria
    val tutoriaEjemplo = Tutoria(
        id = "1",
        fecha = "14 Sep, 2024",
        hora = "10:00 AM",
        modalidad = "Presencial",
        tutor = tutorEjemplo,
        materia = Materia(nombre = "Física II")
    )

    // Crear un Estudiante y agregar la tutoría
    val estudianteEjemplo = Estudiante(
        id = "2",
        nombre = "Estudiante Ejemplo",
        usuario = "estudiante_usuario",
        contraseña = "contraseña123",
        notificaciones = listOf("Nueva tutoría agendada", "Mensaje de tu tutor"),
        misTutorias = mutableListOf(tutoriaEjemplo) // Añadir la tutoría de ejemplo
    )

    // Mostrar la card de tutoría con los datos de ejemplo
    TutoriaCard(infotutoria = tutoriaEjemplo, navController = NavController(LocalContext.current))
}

