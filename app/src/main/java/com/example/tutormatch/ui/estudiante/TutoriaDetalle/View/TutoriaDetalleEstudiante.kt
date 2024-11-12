package com.example.tutormatch.ui.estudiante.TutoriaDetalle.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaConDetallesEstudiante
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.estudiante.TutoriaDetalle.ViewModel.TutoriaDetalleEstudianteViewModel
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.theme.AzulPrimario

@Composable
fun TutoriaDetalleEstudiante(
    tutoriaId: String,
    navController: NavHostController
) {
    val viewModel: TutoriaDetalleEstudianteViewModel = viewModel()
    LaunchedEffect(tutoriaId) {
        viewModel.cargarDetalleTutoria(tutoriaId)
    }
    val detalle by viewModel.tutoriaConDetalles.collectAsState()

    detalle?.let { detalleTutoria ->
        TutoriaDetalleEstudianteContent(detalleTutoria, navController, viewModel)
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun TutoriaDetalleEstudianteContent(
    detalleTutoria: TutoriaConDetallesEstudiante,
    navController: NavHostController,
    viewModel: TutoriaDetalleEstudianteViewModel
) {
    Scaffold(
        topBar = {
            AppBar(title = "Detalles de Tutoría", navController = navController)
        },
        content = { paddingValues ->
            Surface(color = Color.White) {
                Image(
                    painter = painterResource(id = R.drawable.perfil_fondo),
                    contentDescription = "Detalles de Tutoría",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                )

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
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = detalleTutoria.tutor.fotoPerfilUrl.takeIf { it.isNotEmpty() },
                                    placeholder = painterResource(R.drawable.tutor),
                                    error = painterResource(R.drawable.tutor),
                                    contentDescription = "Perfil del tutor",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.White)
                                )

                                Text(
                                    text = detalleTutoria.tutor.nombre,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 25.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Materia
                            Text(
                                text = "Materia :",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = detalleTutoria.materia.nombre,
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            // Modalidad
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Modalidad :",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = detalleTutoria.tutoria.modalidad,
                                fontSize = 20.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            // Fecha y Hora
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Fecha y Hora :",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row {
                                Text(
                                    text = detalleTutoria.tutoria.fecha,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                                Text(
                                    text = detalleTutoria.tutoria.hora,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                            }

                            // Descripción
                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "Descripción :",
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
                                    text = detalleTutoria.tutoria.mensaje,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )
                            }

                            // Botones
                            Spacer(modifier = Modifier.height(30.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        // Navegar a la pantalla de solicitud de tutoría con datos prellenados
                                        navController.navigate(
                                            NavigationState.SolicitudTutoria.createRoute(
                                                detalleTutoria.tutor.id,
                                                detalleTutoria.tutoria.id
                                            )

                                        )
                                        viewModel.cancelarTutoria(detalleTutoria.tutoria.id)

                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "Reagendar")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        viewModel.cancelarTutoria(detalleTutoria.tutoria.id)
                                        navController.popBackStack()
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text(text = "Cancelar")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
