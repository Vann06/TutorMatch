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
    esSolicitud: Boolean = false, // Indica si es una solicitud
    onAceptarSolicitud: ((Tutoria1) -> Unit)? = null, // Función para aceptar la solicitud
    onRechazarSolicitud: ((Tutoria1) -> Unit)? = null // Función para rechazar la solicitud
) {
    // Obtener el nombre de la materia (si es necesario)
    val materiaNombre = remember { mutableStateOf("Materia desconocida") }

    // Obtener la información de la materia desde Firestore
    LaunchedEffect(tutoria.materiaId) {
        val firestore = FirebaseFirestore.getInstance()
        val materiaSnapshot = firestore.collection("materias").document(tutoria.materiaId).get().await()
        val materia = materiaSnapshot.toObject(Materia::class.java)
        materiaNombre.value = materia?.nombre ?: "Materia desconocida"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                // Navegación o acción al hacer clic en la tarjeta
                // Puedes agregar lógica aquí si es necesario
            })
            .padding(8.dp)
            .shadow(12.dp, shape = RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Información principal
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Fecha: ${tutoria.fecha}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Text(
                        text = "Hora: ${tutoria.hora}",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                    Text(
                        text = "Modalidad: ${tutoria.modalidad}",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
                // Mostrar el estado de la tutoría o solicitud
                Text(
                    text = tutoria.estado,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = when (tutoria.estado) {
                        "Aceptada" -> Color.Green
                        "Rechazada" -> Color.Red
                        else -> Color.Yellow
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Mostrar la materia
            Text(
                text = "Materia: ${materiaNombre.value}",
                fontSize = 14.sp,
                color = Color.White
            )
            // Mostrar el mensaje o descripción si existe
            if (tutoria.mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Mensaje: ${tutoria.mensaje}",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            // Si es una solicitud, mostrar botones para aceptar o rechazar
            if (esSolicitud) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onAceptarSolicitud?.invoke(tutoria) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text("Aceptar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onRechazarSolicitud?.invoke(tutoria) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Rechazar")
                    }
                }
            }
        }
    }
}


