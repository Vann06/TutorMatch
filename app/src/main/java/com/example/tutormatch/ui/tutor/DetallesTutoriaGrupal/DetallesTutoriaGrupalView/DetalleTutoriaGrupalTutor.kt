package com.example.tutormatch.ui.tutor.DetallesTutoriaGrupal.DetallesTutoriaGrupalView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaGrupal
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.tutor.DetallesTutoriaGrupal.DetallesTutoriaGrupalViewModel.DetalleTutoriaGrupalTutorViewModel
import com.example.tutormatch.ui.tutor.DetallesTutoriaGrupal.DetallesTutoriaGrupalViewModel.DetalleTutoriaGrupalTutorViewModelFactory

@Composable
fun DetalleTutoriaGrupalTutor(
    tutoriaId: String,
    navController: NavHostController
) {
    val viewModel: DetalleTutoriaGrupalTutorViewModel = viewModel(
        factory = DetalleTutoriaGrupalTutorViewModelFactory(tutoriaId)
    )
    val tutoriaGrupal by viewModel.tutoriaGrupal.collectAsState()
    val estudiantes by viewModel.estudiantes.collectAsState()

    if (tutoriaGrupal != null) {
        DetalleTutoriaGrupalTutorContent(
            tutoriaGrupal = tutoriaGrupal!!,
            estudiantes = estudiantes,
            navController = navController,
            viewModel = viewModel
        )
    } else {
        // Mostrar indicador de carga
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun DetalleTutoriaGrupalTutorContent(
    tutoriaGrupal: TutoriaGrupal,
    estudiantes: List<Estudiante1>,
    navController: NavHostController,
    viewModel: DetalleTutoriaGrupalTutorViewModel
) {
    Scaffold(
        topBar = {
            AppBar(title = "Detalles de Tutoría Grupal", navController = navController)
        },
        content = { paddingValues ->
            Surface(color = Color.White) {
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

                            Spacer(modifier = Modifier.height(16.dp))

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
                                color = Color.Black,
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
                                color = Color.Black,
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
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                                Text(
                                    text = tutoriaGrupal.hora,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                            }

                            // Estudiantes Inscritos
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        item {
                            // Botón para Cancelar la Tutoría
                            Spacer(modifier = Modifier.height(30.dp))

                            Button(
                                onClick = {
                                    viewModel.cancelarTutoriaGrupal(navController)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    Color.Red
                                )
                            ) {
                                Text(
                                    text = "Cancelar Tutoría",
                                    color = Color.White,
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

