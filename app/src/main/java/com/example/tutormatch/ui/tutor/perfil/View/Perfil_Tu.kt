package com.example.tutormatch.ui.tutor.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tutormatch.R
import com.example.tutormatch.navigation.AppBar
import com.example.tutormatch.ui.theme.AzulTerciario
import com.example.tutormatch.ui.theme.GrisPrimario
import com.example.tutormatch.ui.tutor.Perfil.ViewModel.PerfilTutorViewModel
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilTutorScreen(
    navController: NavHostController,
    viewModel: PerfilTutorViewModel = viewModel()
) {
    val tutor by viewModel.tutor.collectAsState()

    Scaffold(
        topBar = {
            AppBar(title = "Perfil Tutor", navController = navController)
        },
        content = { paddingValues ->
            tutor?.let { tutor ->
                Surface(
                    color = GrisPrimario,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Imagen de fondo en la parte superior
                        Image(
                            painter = painterResource(id = R.drawable.perfil_fondo),
                            contentDescription = "Perfil de Usuario",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp) // Ajusta la altura según tus necesidades
                                .align(Alignment.TopStart)
                        )

                        // Contenido desplazable
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            // Espacio para que el contenido comience debajo de la imagen de fondo
                            Spacer(modifier = Modifier.height(160.dp)) // Debe ser un poco menos que la altura de la imagen

                            // Foto y nombre del tutor
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 30.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.tutor),
                                    contentDescription = "Foto de Perfil",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.White)
                                )
                                Text(
                                    text = tutor.nombre,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(start = 35.dp)
                                        .fillMaxWidth()
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Resto del contenido
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 30.dp)
                            ) {
                                // Opciones Expandibles

                                // Usuario
                                ExpandablePerfilItem(
                                    iconResId = R.drawable.user,
                                    title = "Usuario",
                                    initialText = tutor.usuario,
                                    onEdit = { newUsuario ->
                                        val updatedTutor = tutor.copy(usuario = newUsuario)
                                        viewModel.actualizarTutor(updatedTutor)
                                    }
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = AzulTerciario
                                )

                                // Nombre
                                ExpandablePerfilItem(
                                    iconResId = R.drawable.user,
                                    title = "Nombre",
                                    initialText = tutor.nombre,
                                    onEdit = { newName ->
                                        val updatedTutor = tutor.copy(nombre = newName)
                                        viewModel.actualizarTutor(updatedTutor)
                                    }
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = AzulTerciario
                                )

                                // Contraseña
                                ExpandablePerfilItem(
                                    iconResId = R.drawable.eye,
                                    title = "Contraseña",
                                    initialText = "********",
                                    onEdit = { newPassword ->
                                        // Manejar actualización de contraseña
                                    }
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = AzulTerciario
                                )

                                // Notificaciones
                                PerfilItem(
                                    iconResId = R.drawable.bell,
                                    text = "Notificaciones",
                                    hasSwitch = true
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = AzulTerciario
                                )

                                // Materias
                                ExpandablePerfilItem(
                                    iconResId = R.drawable.star,
                                    title = "Materias",
                                    initialText = tutor.materias.joinToString(", "),
                                    onEdit = { newMaterias ->
                                        val materiasList = newMaterias.split(",").map { it.trim() }
                                        val updatedTutor = tutor.copy(materias = materiasList)
                                        viewModel.actualizarTutor(updatedTutor)
                                    }
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = AzulTerciario
                                )

                                // Modalidad
                                MultiSelectPerfilItem(
                                    iconResId = R.drawable.clock_2,
                                    title = "Modalidad",
                                    options = listOf("Virtual", "Presencial"),
                                    selectedOptions = tutor.modalidad.split(", ").map { it.trim() },
                                    onSave = { selectedModalidades ->
                                        val newModalidad = selectedModalidades.joinToString(", ")
                                        val updatedTutor = tutor.copy(modalidad = newModalidad)
                                        viewModel.actualizarTutor(updatedTutor)
                                    }
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = AzulTerciario
                                )


                                // Descripción
                                ExpandablePerfilItem(
                                    iconResId = R.drawable.clock_2,
                                    title = "Descripción",
                                    initialText = tutor.descripcion,
                                    onEdit = { newDescripcion ->
                                        val updatedTutor = tutor.copy(descripcion = newDescripcion)
                                        viewModel.actualizarTutor(updatedTutor)
                                    }
                                )
                                Divider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = AzulTerciario
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }
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
        })
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
            com.example.tutormatch.ui.estudiante.Perfil.View.Button(
                expanded = expanded,
                onClick = { expanded = !expanded })
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
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
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
fun MultiSelectPerfilItem(
    iconResId: Int,
    title: String,
    options: List<String>,
    selectedOptions: List<String>,
    onSave: (List<String>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOptionsState = remember { mutableStateListOf(*selectedOptions.toTypedArray()) }

    Column {
        // Título y botón para expandir
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Icono de opción",
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
            com.example.tutormatch.ui.estudiante.Perfil.View.Button(
                expanded = expanded,
                onClick = { expanded = !expanded })
        }

        // Contenido expandible
        if (expanded) {
            Column(modifier = Modifier.padding(8.dp)) {
                options.forEach { option ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = selectedOptionsState.contains(option),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedOptionsState.add(option)
                                } else {
                                    selectedOptionsState.remove(option)
                                }
                            }
                        )
                        Text(text = option, color = Color.White)
                    }
                }
                Button(
                    onClick = {
                        onSave(selectedOptionsState)
                        expanded = false
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Guardar")
                }
            }
        }
    }
}


