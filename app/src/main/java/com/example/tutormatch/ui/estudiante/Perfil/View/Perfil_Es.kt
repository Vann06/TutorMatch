package com.example.tutormatch.ui.estudiante.Perfil.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.Estudiante
import com.example.tutormatch.ui.theme.AzulTerciario
import com.example.tutormatch.ui.theme.GrisPrimario
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.estudiante.Perfil.ViewModel.PerfilEstudianteViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutormatch.navigation.AppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilEstudianteScreen(
    navController: NavHostController,
    viewModel: PerfilEstudianteViewModel = viewModel()
) {
    val estudiante by viewModel.estudiante.collectAsState()

    Scaffold(
        topBar = {
            AppBar(title = "Perfil", navController = navController)
        },
        content = { paddingValues ->
            estudiante?.let { estudiante ->
                Surface(
                    color = GrisPrimario,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.perfil_fondo),
                        contentDescription = "Perfil de Usuario",
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

                                Image(
                                    painter = painterResource(id = R.drawable.estudiante),
                                    contentDescription = "Foto de Perfil",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.White)
                                )
                                Text(
                                    text = estudiante.nombre,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .fillMaxWidth()
                                )
                            }

                            // Opciones Expandibles
                            ExpandablePerfilItem(
                                iconResId = R.drawable.user,
                                title = "Usuario",
                                initialText = estudiante.usuario,
                                onEdit = { newUsuario ->
                                    val updatedEstudiante = estudiante.copy(usuario = newUsuario)
                                    viewModel.actualizarEstudiante(updatedEstudiante)
                                }
                            )
                            Divider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = AzulTerciario
                            )

                            ExpandablePerfilItem(
                                iconResId = R.drawable.user,
                                title = "Nombre",
                                initialText = estudiante.nombre,
                                onEdit = { newName ->
                                    val updatedEstudiante = estudiante.copy(nombre = newName)
                                    viewModel.actualizarEstudiante(updatedEstudiante)
                                }
                            )
                            Divider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = AzulTerciario
                            )

                            ExpandablePerfilItem(
                                iconResId = R.drawable.eye,
                                title = "Contraseña",
                                initialText = "********",
                                onEdit = { newPassword -> /* Manejar nueva contraseña */ }
                            )
                            Divider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = AzulTerciario
                            )
                            // Opción de notificaciones
                            PerfilItem(
                                iconResId = R.drawable.bell,
                                text = "Notificaciones",
                                hasSwitch = true
                            )
                            Divider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = AzulTerciario
                            )

                            // MyTutors con onClick
                            PerfilItem(
                                iconResId = R.drawable.star,
                                text = "MyTutors",
                                hasSwitch = false,
                                onClick = {
                                    navController.navigate(NavigationState.MyTutors.route)
                                }
                            )
                            Divider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = AzulTerciario
                            )
                        }
                    }
                }
            } ?: run {
                // Mostrar un indicador de carga o mensaje de error
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}


//  Funcion para opciones expandibles y modificar datos
@Composable
fun ExpandablePerfilItem(
    iconResId: Int,
    title: String,
    initialText: String,
    modifier: Modifier = Modifier,
    onEdit: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var editText by remember { mutableStateOf(initialText) }

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Icon de opcion",
                modifier = Modifier.size(45.dp)
            )
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )
            Button(expanded = expanded, onClick = { expanded = !expanded })
        }

        // Verificacion de si se expandio muestra opcion
        if (expanded) {
            TextField(
                value = editText,
                onValueChange = { editText = it },
                label = { Text("Edit $title") }, //Muestra la opcion reciente
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Button(
                onClick = { onEdit(editText) },
                modifier = Modifier.align(Alignment.End).padding(8.dp)
            ) {
                Text(text = "Guardar")
            }
        }
    }
}


// Un item del perfil que no necesita expansion
@Composable
fun PerfilItem(
    iconResId: Int,
    text: String,
    hasSwitch: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = Modifier.clickable { onClick?.invoke() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Icon",
                modifier = Modifier.size(45.dp)
            )

            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            )

            if (hasSwitch) {
                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
            }
        }
    }
}


@Composable
fun Button(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "Expand button",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(30.dp)
        )
    }
}

