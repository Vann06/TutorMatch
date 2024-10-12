package com.example.tutormatch.ui.tutor.Estudiante_Tu.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import coil.compose.AsyncImage
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.TutoriaConDetalles
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.theme.AzulPrimario

@Composable
fun EstudianteTuContenido(solicitud: TutoriaConDetalles){

    Surface(color = Color.White) {

        Image(
            painter = painterResource(id = R.drawable.perfil_fondo),
            contentDescription = "Solicitud de Estudiante",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    AsyncImage(
                        model = solicitud.estudiante.fotoPerfilUrl.takeIf { it.isNotEmpty() },
                        placeholder = painterResource(R.drawable.estudiante),
                        error = painterResource(R.drawable.estudiante),
                        contentDescription = "Perfil del tutor",
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

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ){
                    Button(
                        onClick = {
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Aceptar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
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
