package com.example.tutormatch.ui.estudiante.TutoriaGrupal.TutoriaGrupalView

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.firebaseImplementation.Tutor1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.ui.estudiante.TutoriaGrupal.TutoriaGrupalViewModel.DetalleTutoriaGrupalViewModel
import com.example.tutormatch.ui.estudiante.TutoriaGrupal.TutoriaGrupalViewModel.DetalleTutoriaGrupalViewModelFactory
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.theme.AzulPrimario

@Composable
fun DetalleTutoriaGrupal(
    tutoriaId: String,
    navController: NavHostController
) {
    val viewModel: DetalleTutoriaGrupalViewModel = viewModel(
        factory = DetalleTutoriaGrupalViewModelFactory(tutoriaId)
    )
    val tutoriaGrupal by viewModel.tutoriaGrupal.collectAsState()
    val tutor by viewModel.tutor.collectAsState()
    val isInscrito by viewModel.isEstudianteInscrito.collectAsState()

    if (tutoriaGrupal != null && tutor != null) {
        if (tutoriaGrupal!!.estado == "Cancelada") {
            // Mostrar mensaje de que la tutoría ha sido cancelada
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Esta tutoría ha sido cancelada.", color = Color.Red)
            }
        } else {
            DetalleTutoriaGrupalContent(
                tutoriaGrupal = tutoriaGrupal!!,
                tutor = tutor!!,
                isInscrito = isInscrito,
                navController = navController,
                viewModel = viewModel
            )
        }
    } else {
        // Mostrar indicador de carga
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun DetalleTutoriaGrupalContent(
    tutoriaGrupal: TutoriaGrupal,
    tutor: Tutor1,
    isInscrito: Boolean,
    navController: NavHostController,
    viewModel: DetalleTutoriaGrupalViewModel
) {
    Scaffold(
        topBar = {
            AppBar(title = "Detalles de Tutoría Grupal", navController = navController)
        },
        content = { paddingValues ->
            Surface(color = MaterialTheme.colorScheme.background) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        item {
                            // Mostrar detalles de la tutoría grupal
                            // Similar a TutoriaDetalleEstudianteContent, pero adaptado para TutoriaGrupal

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = tutor.fotoPerfilUrl.takeIf { it.isNotEmpty() },
                                    placeholder = painterResource(R.drawable.tutor),
                                    error = painterResource(R.drawable.tutor),
                                    contentDescription = "Perfil del tutor",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colorScheme.onBackground)
                                )

                                Text(
                                    text = tutor.nombre,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 25.sp,
                                    color = AzulClaro,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Materia
                            Text(
                                text = "Materia:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = tutoriaGrupal.materiaId,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            // Modalidad
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Modalidad:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = tutoriaGrupal.modalidad,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            // Fecha y Hora
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Fecha y Hora:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row {
                                Text(
                                    text = tutoriaGrupal.fecha,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                                Text(
                                    text = tutoriaGrupal.hora,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                            }
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Descripción",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )
                            Text(
                                text = tutoriaGrupal.mensaje,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            // Cupos disponibles
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Cupos Disponibles:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "${tutoriaGrupal.cuposMaximos - tutoriaGrupal.estudiantesInscritos.size}",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            // Botón para Inscribirse o Desinscribirse
                            Spacer(modifier = Modifier.height(30.dp))

                            Button(
                                onClick = {
                                    if (isInscrito) {
                                        viewModel.desinscribirseDeTutoriaGrupal()
                                    } else {
                                        viewModel.inscribirseEnTutoriaGrupal()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    if (isInscrito) MaterialTheme.colorScheme.error else AzulPrimario
                                )
                            ) {
                                Text(
                                    text = if (isInscrito) "Desinscribirse" else "Inscribirse",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
