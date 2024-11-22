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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Materia
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.theme.AzulPrimario
import com.example.tutormatch.ui.tutor.MisTutorias.ViewModel.MisTutoriasViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisTutoriasScreen(navController: NavHostController) {
    val tabs = listOf("Mis Tutorías", "Solicitudes")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            AppBar(title = "Tutorías", navController = navController)
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { navController.navigate(NavigationState.PerfilTutor.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Tutorías") },
                    label = { Text("Tutorías") },
                    selected = true,
                    onClick = { /* Ya estamos en esta pantalla */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Crear Tutoría") },
                    label = { Text("Crear Tutoría") },
                    selected = false,
                    onClick = { navController.navigate(NavigationState.CrearTutoria.route) }
                )
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = AzulPrimario,
                    contentColor = Color.White
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.LightGray
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> {
                        MisTutoriasContent(navController = navController)
                    }
                    1 -> {
                        SolicitudesContent(navController = navController)
                    }
                }
            }
        }
    )
}
@Composable
fun TutoriaCard(
    navController: NavController,
    tutoria: Tutoria1,
    esSolicitud: Boolean = false
) {
    // Estados para almacenar la información
    val materiaNombre = remember { mutableStateOf(tutoria.materiaId) }
    val estudiante = remember { mutableStateOf<Estudiante1?>(null) }

    // Obtener la información del estudiante
    LaunchedEffect(tutoria.estudianteId) {
        val firestore = FirebaseFirestore.getInstance()
        val estudianteSnapshot = firestore.collection("estudiantes").document(tutoria.estudianteId).get().await()
        val estudianteData = estudianteSnapshot.toObject(Estudiante1::class.java)
        estudiante.value = estudianteData
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                // Navegar a la vista detallada de la solicitud
                navController.navigate(NavigationState.Estudiante_Tu.createRoute(tutoria.id))
            })
            .padding(8.dp)
            .shadow(12.dp, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424242) // Color gris oscuro
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Foto de perfil del estudiante
                AsyncImage(
                    model = estudiante.value?.fotoPerfilUrl.takeIf { !it.isNullOrEmpty() },
                    placeholder = painterResource(R.drawable.estudiante),
                    error = painterResource(R.drawable.estudiante),
                    contentDescription = "Perfil del estudiante",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    // Título: Materia solicitada
                    Text(
                        text = materiaNombre.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    // Subtítulo: Nombre del estudiante
                    Text(
                        text = estudiante.value?.nombre ?: "Estudiante desconocido",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Mensaje del estudiante
            Text(
                text = tutoria.mensaje,
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TutoriaGrupalCard(
    navController: NavController,
    tutoriaGrupal: TutoriaGrupal
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = {
                // Navegar a la vista detallada de la tutoría grupal
                navController.navigate(NavigationState.DetalleTutoriaGrupalTutor.createRoute(tutoriaGrupal.id))
            }),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF424242) // Color gris oscuro
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Información de la tutoría grupal
            Text(
                text = "Materia: ${tutoriaGrupal.materiaId}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = "Fecha: ${tutoriaGrupal.fecha} - Hora: ${tutoriaGrupal.hora}",
                fontSize = 14.sp,
                color = Color.LightGray
            )
            Text(
                text = "Cupos disponibles: ${tutoriaGrupal.cuposMaximos - tutoriaGrupal.estudiantesInscritos.size}",
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }
    }
}
