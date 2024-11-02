package com.example.tutormatch.ui.estudiante.MyTutors.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun MyTutorsScreen(
    navController: NavController,
    viewModel: MyTutorsViewModel = viewModel()
) {
    val infoEstudiante = viewModel.estudiante.observeAsState()
    val tutorias = viewModel.tutorias.observeAsState()

    infoEstudiante.value?.let { estudiante ->
        tutorias.value?.let { listaTutorias ->
            MyTutors(
                infoEstudiante = estudiante,
                listaTutorias = listaTutorias,
                navController = navController
            )
        }
    }
}

@Composable
fun MyTutors(
    infoEstudiante: Estudiante1,
    listaTutorias: List<Tutoria1>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
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
