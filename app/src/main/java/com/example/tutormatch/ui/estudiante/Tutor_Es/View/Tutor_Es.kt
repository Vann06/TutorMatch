package com.example.tutormatch.ui.estudiante.Tutor_Es.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.estudiante.Tutor_Es.ViewModel.PerfilTutorEstudianteViewModel
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.theme.AzulPrimario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilTutorEstudiante(
    tutor: Tutor1,
    navController: NavHostController,
    viewModel: PerfilTutorEstudianteViewModel
) {
    val tutoriasGrupales by viewModel.tutoriasGrupales.collectAsState()

    Scaffold(
        topBar = {
            AppBar(title = "", navController = navController)
        },
        content = { paddingValues ->
            Surface(color = MaterialTheme.colorScheme.background) {
                Image(
                    painter = painterResource(id = R.drawable.perfil_fondo),
                    contentDescription = "Perfil del tutor",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.TopStart
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp)
                            .padding(top = 16.dp, bottom = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Imagen de perfil
                                if (tutor.fotoPerfilUrl.isNotEmpty()) {
                                    Image(
                                        painter = rememberAsyncImagePainter(tutor.fotoPerfilUrl),
                                        contentDescription = "Foto de Perfil",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, Color.White)
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.tutor),
                                        contentDescription = "Foto de Perfil",
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, Color.White)
                                    )
                                }

                                Text(
                                    text = tutor.nombre,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 25.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Sección Modalidad
                            Text(
                                text = "Modalidad:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                if (tutor.modalidad.contains("Virtual")) {
                                    Text(
                                        text = "- Virtual",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                }
                                if (tutor.modalidad.contains("Presencial")) {
                                    Text(
                                        text = "- Presencial",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Sección Descripción
                            Text(
                                text = "Descripción:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )
                            Spacer(modifier = Modifier.height(10.dp))
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
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                            Spacer(modifier = Modifier.height(30.dp))

                            // Botón "Agendar Tutoría"
                            Button(
                                onClick = {
                                    val tutorId = tutor.id
                                    navController.navigate("SolicitudTutoria/$tutorId")
                                },
                                modifier = Modifier
                                    .align(Alignment.Center)
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

                            Text(
                                text = "Tutorías Grupales Disponibles",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = AzulClaro,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        // Ahora, fuera del 'item', llamamos a 'items' para las tutorías grupales
                        items(tutoriasGrupales) { tutoriaGrupal ->
                            TutoriaGrupalCard(
                                tutoriaGrupal = tutoriaGrupal,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun TutoriaGrupalCard(
    tutoriaGrupal: TutoriaGrupal,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                navController.navigate(NavigationState.DetalleTutoriaGrupal.createRoute(tutoriaGrupal.id))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = tutoriaGrupal.materiaId,
                style = MaterialTheme.typography.titleMedium,
                color = AzulPrimario
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Fecha: ${tutoriaGrupal.fecha} - Hora: ${tutoriaGrupal.hora}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Cupos disponibles: ${tutoriaGrupal.cuposMaximos - tutoriaGrupal.estudiantesInscritos.size}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

