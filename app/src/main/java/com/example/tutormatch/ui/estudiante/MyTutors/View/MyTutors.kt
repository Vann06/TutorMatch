package com.example.tutormatch.ui.estudiante.MyTutors.View

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutormatch.R
import com.example.tutormatch.ui.estudiante.MyTutors.ViewModel.MyTutorsViewModel
import com.example.tutormatch.ui.theme.AzulPrimario
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.Tutoria1

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.navigation.NavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTutorsScreen(
    navController: NavHostController,
    viewModel: MyTutorsViewModel = viewModel()
) {
    val tutorias = viewModel.tutorias.observeAsState()

    Scaffold(
        topBar = {
            AppBar(title = "Mis Tutores", navController = navController)
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        content = { paddingValues ->
            tutorias.value?.let { listaTutorias ->
                if (listaTutorias.isNotEmpty()) {
                    MyTutors(
                        listaTutorias = listaTutorias,
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                } else {
                    // Mostrar mensaje de que no hay tutorías
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No tienes tutorías aún")
                    }
                }
            } ?: run {
                // Mostrar indicador de carga
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}

@Composable
fun MyTutors(
    listaTutorias: List<Tutoria1>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(listaTutorias) { tutoria ->
            TutoriaCard(
                infotutoria = tutoria,
                navController = navController
            )
        }
    }
}


@Composable
fun TutoriaCard(
    infotutoria: Tutoria1,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navegación al detalle de la tutoría, usando el ID del tutor o de la tutoría
                navController.navigate("detalle_tutoria/${infotutoria.id}")
            }
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
                contentDescription = "Imagen de perfil de tutor",
                modifier = Modifier
                    .padding(6.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            // Columna con ID del tutor y modalidad
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "Tutor ID: ${infotutoria.tutorId}",
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

                // Materia con Card circular
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = AzulPrimario
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Materia ID: ${infotutoria.materiaId}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            // Columna con fecha y hora
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

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentRoute == NavigationState.Perfil_Es.route,
            onClick = {
                navController.navigate(NavigationState.Perfil_Es.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Buscador") },
            label = { Text("Buscador") },
            selected = currentRoute == NavigationState.Main_Es.route,
            onClick = {
                navController.navigate(NavigationState.Main_Es.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Mis Tutores") },
            label = { Text("Mis Tutores") },
            selected = currentRoute == NavigationState.MyTutors.route,
            onClick = {
                navController.navigate(NavigationState.MyTutors.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
