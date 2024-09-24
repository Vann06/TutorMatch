package com.example.tutormatch.ui.vistas.general

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutormatch.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.tutormatch.ui.theme.AzulClaro
import com.example.tutormatch.ui.theme.AzulPrimario

@Composable
fun LoginScreen(navController: NavHostController, modifier: Modifier = Modifier){
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo1),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column (
            modifier = Modifier
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(text = "TUTO!",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {

                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    RoundedCornerShape(16.dp),
                   // border = BorderStroke(2.dp, Color.Black),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF606060)
                    ),

                ){
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text = "LOGIN",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(10.dp)
                        )

                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    Text(text = "Username",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6C8AF4),
                        modifier = Modifier.padding(20.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = user,
                        onValueChange = { user = it },
                        label = { Text("Correo Electrónico", color = Color.White)},
                        placeholder = { Text("usuario@ejemplo.com") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Contraseña",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AzulClaro,
                        modifier = Modifier.padding(20.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña", color = Color.White) },
                        placeholder = { Text("Ingresa tu contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Button(onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(AzulPrimario),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .width(250.dp)
                        ) {
                            Text(text = "LOGIN",
                                fontSize = 25.sp,
                                color = Color.White,)

                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInScreenPreview() {
    LoginScreen(navController = NavHostController(context = LocalContext.current))
}