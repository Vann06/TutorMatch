package com.example.tutormatch.ui.tutor.MisTutorias.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.tutor.MisTutorias.ViewModel.MisTutoriasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisTutoriasScreen(navController: NavHostController, viewModel: MisTutoriasViewModel = viewModel()) {
    val misTutorias by viewModel.misTutorias.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = {  navController.navigate(NavigationState.PerfilTutor.route) {
                        launchSingleTop = true
                        restoreState = true
                    } }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Tutorías") },
                    label = { Text("Tutorías") },
                    selected = false,
                    onClick = { navController.navigate(NavigationState.MisTutorias.route) {
                        launchSingleTop = true
                        restoreState = true
                    } }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Crear Tutoría") },
                    label = { Text("Crear Tutoría") },
                    selected = false,
                    onClick = {  navController.navigate(NavigationState.CrearTutoria.route) {
                        launchSingleTop = true
                        restoreState = true
                    }}
                )
            }
        },
        topBar = {
            AppBar(title = "Tutorías", navController = navController)
        },
        content = { paddingValues ->
            if (misTutorias.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(misTutorias) { tutoria ->
                        TutoriaCard(navController = navController, infotutoria = tutoria)
                    }
                }
            } else {
                // Mostrar mensaje de que no hay tutorías
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No tienes tutorías pendientes")
                }
            }
        }
    )
}


@Composable
fun TutoriaCard(navController: NavController, infotutoria: Tutoria1) {
    val nombreTutor = infotutoria.tutorId // Aquí podrías usar un método para obtener el nombre del tutor si se requiere

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate(NavigationState.MisTutorias)
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
            Image(
                painter = painterResource(id = R.drawable.estudiante),
                contentDescription = "Imagen de perfil de tutor",
                modifier = Modifier
                    .padding(6.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = nombreTutor ?: "Tutor desconocido",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
                Text(
                    text = "Modalidad: " + infotutoria.modalidad,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Blue
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .wrapContentSize()
                ) {
                    Text(
                        text = infotutoria.materiaId ?: "Materia desconocida",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Fecha: " + infotutoria.fecha,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = Color.LightGray
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
    val materiaEjemplo = Materia(
        id = "1",
        nombre = "Física II"
    )

    val tutoriaEjemplo = Tutoria1(
        id = "1",
        estudianteId = "2",
        tutorId = "1",
        materiaId = materiaEjemplo.id,
        fecha = "14 Sep, 2024",
        hora = "10:00 AM",
        modalidad = "Presencial",
        mensaje = "Por favor, revisar temas de mecánica",
        estado = "Pendiente"
    )

    val estudianteEjemplo = Estudiante1(
        id = "2",
        nombre = "Estudiante Ejemplo",
        usuario = "estudiante_usuario",
        fotoPerfilUrl = "url_de_imagen",
        email = "estudiante@ejemplo.com",
        tutoresIds = listOf("1")
    )

    TutoriaCard(infotutoria = tutoriaEjemplo, navController = NavController(LocalContext.current))
}
