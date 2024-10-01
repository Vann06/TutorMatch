package com.example.tutormatch.ui.vistas.estudiante

import peroandroidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.Estudiante
import com.example.tutormatch.ui.theme.AzulTerciario
import com.example.tutormatch.ui.theme.GrisPrimario
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.ui.theme.GrisSecundario
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.compose.rememberNavController
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.theme.AzulPrimario
import com.example.tutormatch.ui.theme.AzulSecundario


@Composable
fun PerfilTutorEstudiante(
    tutor: Tutor,
    navController: NavHostController
) {
    Surface(color = Color.White) {
        Image(
            painter = painterResource(id = R.drawable.perfil_fondo),
            contentDescription = "Perfil del tutor",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.tutor),
                        contentDescription = "Foto de Perfil",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White)
                    )
                    Text(
                        text = tutor.nombre,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 25.sp,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sección Modalidad
                Text(
                    text = "Modalidad :",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = AzulClaro
                )
                Row {
                    if (tutor.modalidad.contains("Virtual")) {
                        Text(
                            text = "- Virtual",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                    }
                    if (tutor.modalidad.contains("Presencial")) {
                        Text(
                            text = "- Presencial",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sección Descripción
                Text(
                    text = "Descripción :",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = AzulClaro
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .border(2.dp, AzulPrimario, shape = RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = tutor.descripcion,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Botón "Agendar Tutoría"
                Button(
                    onClick = { /* Lógica para agendar tutoría */ },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)  // Centrar botón
                        .padding(vertical = 8.dp)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(AzulPrimario)
                ) {
                    Text(
                        text = "Agendar Tutoría",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Botón "Tutoría Grupal"
                Button(
                    onClick = { /* Lógica para agendar tutoría grupal */ },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(AzulPrimario)                ) {
                    Text(
                        text = "Tutoría Grupal",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPerfilTutorEstudiante() {
    // Instancia de prueba para el Tutor
    val tutorPrueba = Tutor(
        id = "1",
        nombre = "Juan Pérez",
        usuario = "jperez",
        contraseña = "12345",
        myStudents = mutableListOf(),
        fotoPerfil = R.drawable.tutor,
        materias = mutableListOf(),
        descripcion = "Soy un tutor con experiencia en matemáticas y física. Me gusta enseñar de forma interactiva.",
        modalidad = "Virtual, Presencial"
    )

    // Llamada a la función para mostrar la vista previa
    PerfilTutorEstudiante(
        tutor = tutorPrueba,
        navController = rememberNavController()
    )
}
