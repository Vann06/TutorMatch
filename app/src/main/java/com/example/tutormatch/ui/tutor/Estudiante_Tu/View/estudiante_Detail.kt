package com.example.tutormatch.ui.tutor.Estudiante_Tu.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.firebaseImplementation.TutoriaConDetalles
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.theme.AzulPrimario
import com.example.tutormatch.ui.tutor.Estudiante_Tu.ViewModel.EstudianteTuViewModel

@Composable
fun EstudianteTuContenido(
    solicitud: TutoriaConDetalles,
    navController: NavHostController,
    viewModel: EstudianteTuViewModel
) {
    Scaffold(
        topBar = {
            AppBar(title = "Detalles de Tutoría", navController = navController)
        },
        content = { paddingValues ->
            Surface(color = Color.White) {
                Image(
                    painter = painterResource(id = R.drawable.perfil_fondo),
                    contentDescription = "Solicitud de Estudiante",
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
                                    model = solicitud.estudiante.fotoPerfilUrl.takeIf { it.isNotEmpty() },
                                    placeholder = painterResource(R.drawable.estudiante),
                                    error = painterResource(R.drawable.estudiante),
                                    contentDescription = "Perfil del estudiante",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.White)
                                )

                                Text(
                                    text = solicitud.estudiante.nombre,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 25.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Continúa con el resto de tus componentes
                            // Materia
                            Text(
                                text = "Materia :",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = AzulClaro
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = solicitud.materia.nombre,
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
                                text = solicitud.tutoria.modalidad,
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
                                    text = solicitud.tutoria.fecha,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                                Text(
                                    text = solicitud.tutoria.hora,
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
                                    text = solicitud.tutoria.mensaje,
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
                                        viewModel.aceptarSolicitud(solicitud.tutoria.id)
                                        navController.popBackStack()
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "Aceptar")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = {
                                        viewModel.rechazarSolicitud(solicitud.tutoria.id)
                                        navController.popBackStack()
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text(text = "Rechazar")
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}