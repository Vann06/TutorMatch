package com.example.tutormatch.ui.tutor.Perfil.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tutormatch.R
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.ui.theme.AzulTerciario
import com.example.tutormatch.ui.theme.GrisPrimario


@Composable
fun PerfilTutorScreen(
    tutor: Tutor,
    navController: NavHostController
){
    Surface(color = GrisPrimario){
        Image(
            painter = painterResource(id = R.drawable.perfil_fondo),
            contentDescription = "Perfil de Usuario",
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)

        )

        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalAlignment = Alignment.Start
            ){
                Spacer(modifier = Modifier.height(60.dp))

                Row(verticalAlignment = Alignment.CenterVertically){
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

                //Opciones Expandibles
                ExpandablePerfilItem(
                    iconResId = R.drawable.user,
                    title = "Usuario",
                    initialText = tutor.usuario,
                    onEdit = { newUsuario -> /* Manejar nuevo usuario */ }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = AzulTerciario
                )
                ExpandablePerfilItem(
                    iconResId = R.drawable.user,
                    title = "Nombre",
                    initialText = tutor.nombre,
                    onEdit = { newName -> /* Manejar nuevo nombre */ }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = AzulTerciario
                )
                ExpandablePerfilItem(
                    iconResId = R.drawable.eye,
                    title = "Contraseña",
                    initialText = "********",
                    onEdit = { newPassword -> /* Manejar nueva contraseña */ }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = AzulTerciario
                )

                // Opción de notificaciones
                PerfilItem(
                    iconResId = R.drawable.bell,
                    text = "Notificaciones",
                    hasSwitch = true
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = AzulTerciario
                )
                ExpandablePerfilItem(
                    iconResId = R.drawable.star,
                    title = "Materias",
                    initialText = tutor.materias.toString(),
                    onEdit = { newMaterias -> /* Manejar nueva contraseña */ }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = AzulTerciario
                )
                ExpandablePerfilItem(
                    iconResId = R.drawable.clock_2,
                    title = "Modalidad",
                    initialText = tutor.modalidad,
                    onEdit = { newModalidad -> /* Manejar nueva contraseña */ }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = AzulTerciario
                )


            }
            }
        }
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


@Preview(showBackground = true)
@Composable
fun PerfilPreview(){
    PerfilTutorScreen(tutor = Tutor(
            id = "1",
    nombre = "Juan Pérez",
    usuario = "jperez",
    contraseña = "12345",
    myStudents = mutableListOf(),
    fotoPerfil = R.drawable.tutor,
    materias = mutableListOf(),
    descripcion = "Soy un tutor con experiencia en matemáticas y física. Me gusta enseñar de forma interactiva.",
    modalidad = "Virtual, Presencial"
    ) , navController = NavHostController(context = LocalContext.current))
}