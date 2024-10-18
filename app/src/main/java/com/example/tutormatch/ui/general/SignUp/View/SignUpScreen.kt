package com.example.tutormatch.ui.general.SignUp.View

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tutormatch.R
import com.example.tutormatch.ui.general.SignUp.Repository.AuthRepository
import com.example.tutormatch.ui.general.SignUp.ViewModel.SignUpViewModel
import com.example.tutormatch.ui.theme.AzulPrimario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignUpScreen(navController: NavHostController) {
    val auth = FirebaseAuth.getInstance() // Obtén la instancia de FirebaseAuth
    val firestore = FirebaseFirestore.getInstance() // Obtén la instancia de Firestore

    val authRepository = AuthRepository(auth = auth, firestore = firestore) // Pasa las instancias
    val signUpViewModel = SignUpViewModel(authRepository) // Pasa el repo al ViewModel

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedAccountType by remember { mutableStateOf<String?>(null) }

    val isLoading by signUpViewModel.isLoading.collectAsState()
    val errorMessage by signUpViewModel.errorMessage.collectAsState()
    val successMessage by signUpViewModel.successMessage.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF1976D2))
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo1),
            contentDescription = "Fondo de pantalla",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Mostrar error o éxito
        errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.align(Alignment.TopCenter).padding(16.dp))
        }

        successMessage?.let {
            Text(text = it, color = Color.Green, modifier = Modifier.align(Alignment.TopCenter).padding(16.dp))
            // Navega a otra pantalla si es necesario
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título principal
            Text(
                text = "TUTO!",
                fontWeight = FontWeight.Bold,
                fontSize = 43.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Tarjeta con los campos de registro
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SIGNUP",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Campo de Nombre
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.LightGray,
                            cursorColor = Color.White
                        )
                    )

                    // Campo de Email
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        placeholder = { Text("ejemplo@email.com") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.LightGray,
                            cursorColor = Color.White
                        )
                    )

                    // Campo de Contraseña
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Clear
                            else Icons.Filled.Star

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.LightGray,
                            cursorColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Texto de selección de cuenta
                    Text(
                        text = "Elige tu tipo de cuenta:",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Botones de tipo de cuenta
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { selectedAccountType = "Estudiante" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedAccountType == "Estudiante") Color(0xFF3E54C2) else Color.Gray,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(text = "Estudiante")
                        }

                        Button(
                            onClick = { selectedAccountType = "Tutor" },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedAccountType == "Tutor") Color(0xFF3E54C2) else Color.Gray,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(text = "Tutor")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de registro
                    Button(
                        onClick = {
                            // Verifica que se haya seleccionado un tipo de cuenta
                            if (selectedAccountType.isNullOrEmpty()) {
                                // Muestra un mensaje de error o realiza alguna acción
                                signUpViewModel.setError("Por favor, selecciona un tipo de cuenta.")
                            } else {
                                signUpViewModel.signUp(
                                    email = email,
                                    password = password,
                                    name = name,
                                    accountType = selectedAccountType ?: "",
                                    navController = navController
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AzulPrimario,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text(text = "SIGNUP", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Mostrar un indicador de carga
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

