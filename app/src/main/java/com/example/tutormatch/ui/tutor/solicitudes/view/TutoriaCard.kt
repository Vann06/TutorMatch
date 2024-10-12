package com.example.tutormatch.ui.tutor.solicitudes.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.TutoriaConDetalles
import com.example.tutormatch.ui.tutor.solicitudes.ViewModel.TutoriaViewModel

@Composable
fun TutoriaCard(solicitud: TutoriaConDetalles, viewModel: TutoriaViewModel) {
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                model = if (solicitud.estudiante.fotoPerfilUrl.isNotEmpty())
                    solicitud.estudiante.fotoPerfilUrl
                else
                    R.drawable.estudiante,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = solicitud.materia.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Por: ${solicitud.estudiante.nombre}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Tutor: ${solicitud.tutoria.mensaje}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

