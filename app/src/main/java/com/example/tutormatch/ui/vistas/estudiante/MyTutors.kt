package com.example.tutormatch.ui.vistas.estudiante

import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.Estudiante
import com.example.tutormatch.ui.theme.AzulPrimario
import com.example.tutormatch.ui.theme.AzulSecundario
import com.example.tutormatch.ui.theme.AzulTerciario
import com.example.tutormatch.estructuras.Materias
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.estructuras.Tutoria


@Composable
fun MyTutors(infoEstudiante: Estudiante) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(infoEstudiante.misTutorias) { tutoria ->
            TutoriaCard(infotutoria = tutoria)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewMyTutors() {
    val exampleEstudiante = Estudiante(
        nombre = "Ejemplo Estudiante",
        misTutorias = mutableListOf(
            Tutoria(
                tutor = Tutor(nombre = "Ricardo Godinez"),
                modalidad = "Virtual",
                materia = Materias(nombre = "Física 1"),
                fecha = "02/10/24",
                hora = "17:35"
            ),
            Tutoria(
                tutor = Tutor(nombre = "Diego López"),
                modalidad = "Presencial",
                materia = Materias(nombre = "Matemáticas"),
                fecha = "03/10/24",
                hora = "14:00"
            )
        )
    )

    MyTutors(infoEstudiante = exampleEstudiante)
}



@Composable
fun TutoriaCard(infotutoria: Tutoria) {

    val nombreTutor = infotutoria.tutor.nombre

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { /* Aquí va la acción al hacer clic */ })
            .padding(8.dp)
            .shadow(12.dp, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray // Fondo oscuro como en la imagen
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
                contentDescription = "Imagen de perfil de tutor",
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
                Text(
                    text = nombreTutor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp, // Tamaño de texto más pequeño
                    color = Color.White // Texto en blanco
                )
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
                        containerColor = AzulPrimario // Fondo azul
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

//@Preview(showBackground = true)
@Composable
fun PreviewTutoriaCard() {
    // Crear datos de ejemplo para la tutoria
    val tutorEjemplo = Tutor(
        id = "1",
        nombre = "Tutor Ejemplo",
        usuario = "usuario",
        contraseña = "contraseña",
        myStudents = mutableListOf(),
        fotoPerfil = R.drawable.estudiante, // Cambiar por un drawable válido
        materias = mutableListOf(
            Materias(nombre = "Física II")
        ),
        descripcion = "Descripción",
        modalidad = "Online"
    )

    val estudianteEjemplo = Estudiante(
        id = "2",
        nombre = "Estudiante Ejemplo",
        usuario = "estudiante_usuario",
        contraseña = "contraseña123",
        notificaciones = listOf("Nueva tutoría agendada", "Mensaje de tu tutor"),
        misTutorias = mutableListOf(), // Lista vacía para comenzar
        fotoPerfil = "https://example.com/imagen_perfil.jpg" // O un drawable si es local
    )

    val materiaEjemplo = Materias(
        nombre = "Física II"
    )

    val tutoriaEjemplo = Tutoria(
        id = "1",
        fecha = "14 Sep, 2024",
        hora = "10:00 AM",
        modalidad = "Presencial",
        tutor = tutorEjemplo,
        materia = materiaEjemplo
    )

    // Mostrar la card de tutoría con los datos de ejemplo
    TutoriaCard(infotutoria = tutoriaEjemplo)
}
