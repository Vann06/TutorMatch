package com.example.tutormatch.ui.tutor.solicitudes.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutormatch.ui.tutor.solicitudes.ViewModel.TutoriaViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutormatch.R

@Composable
fun SolicitudesScreen(){
    val viewModel: TutoriaViewModel = viewModel()
    val solicitudes by viewModel.solicitudesTutoria.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { /* Maneja la navegación a Inicio */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Event, contentDescription = "Grupal") },
                    label = { Text("Grupal") },
                    selected = false,
                    onClick = { /* Maneja la navegación a Más */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "MyStudents") },
                    label = { Text("MyStudents") },
                    selected = false,
                    onClick = { /* Maneja la navegación a Más */ }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Surface {
                Image(
                    painter = painterResource(id = R.drawable.perfil_fondo),
                    contentDescription = "Fondo de pantalla",
                )


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Tutorias",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(16.dp),
                        color = colorResource(id = R.color.white)

                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (solicitudes.isEmpty()) {
                            Spacer(modifier = Modifier.height(170.dp))
                            Text(
                                text = "No hay solicitudes de tutoría",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        } else {
                            Spacer(modifier = Modifier.height(100.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                items(solicitudes) { solicitud ->
                                    TutoriaCard(solicitud, viewModel)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSolicitudesTutoriaScreen() {
    SolicitudesScreen()
}
