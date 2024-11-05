package com.example.tutormatch.ui.tutor.solicitudes.view

/*
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
                model = solicitud.estudiante.fotoPerfilUrl.takeIf { it.isNotEmpty() },
                placeholder = painterResource(R.drawable.estudiante),
                error = painterResource(R.drawable.estudiante),
                contentDescription = "Perfil del tutor",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White)
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

*/