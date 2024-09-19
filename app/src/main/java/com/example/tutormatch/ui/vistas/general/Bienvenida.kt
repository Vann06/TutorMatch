package com.example.tutormatch.ui.vistas.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutormatch.R
import com.example.tutormatch.ui.theme.Azul04

@Composable
fun Bienvenida(onLoginClick: () -> Unit = {}, onSignUpClick: () -> Unit = {}) {
    // Caja
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo con logo
        Image(
            painter = painterResource(id = R.drawable.gato_inicio),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop, // llenar toda la pantalla
            modifier = Modifier.fillMaxSize() // Ocupa toda la pantalla
        )

        // Column para colocar el texto y los botones encima de la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Espacio al inicio para dejar margen
            Spacer(modifier = Modifier.height(200.dp))

            // Título de la App encima del fondo
            Text(
                text = "TUTORMATCH",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )


            // Botones de SignIn y SignUp en la parte inferior
            Column(modifier = Modifier
                                .fillMaxSize()
                                .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(150.dp))

                Button(
                    onClick = onLoginClick,
                    colors = ButtonDefaults.buttonColors(Azul04),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .width(250.dp)
                ) {
                    Text(text = "Iniciar Sesión",
                        fontSize = 25.sp)
                }
                Button(
                    onClick = onSignUpClick,
                    colors = ButtonDefaults.buttonColors(Azul04),
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .width(250.dp)
                ) {
                    Text(text = "Crear Cuenta",
                        fontSize = 25.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    Bienvenida()
}
