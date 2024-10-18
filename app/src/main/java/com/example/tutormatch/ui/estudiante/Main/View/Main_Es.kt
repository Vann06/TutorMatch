package com.example.tutormatch.ui.estudiante.Main.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tutormatch.R
import com.example.tutormatch.ui.estudiante.Main.Repository.EstudianteRepository
import com.example.tutormatch.ui.estudiante.Main.ViewModel.MainEstudianteViewModel
import com.example.tutormatch.ui.theme.AzulPrimario
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainEstudiante(
    navController: NavHostController,
    viewModel: MainEstudianteViewModel = MainEstudianteViewModel(EstudianteRepository(
        FirebaseFirestore.getInstance()))
) {
    val materias by viewModel.materias.collectAsState()
    val tutors by viewModel.tutors.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var isDropdownVisible by remember { mutableStateOf(false) }

    val filteredMaterias = materias.filter { materia ->
        materia.nombre.contains(searchQuery, ignoreCase = true)
    }.toMutableList()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { /* Navegación a Perfil */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Buscador") },
                    label = { Text("Buscador") },
                    selected = false,
                    onClick = { /* Navegación a Buscador */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "MyTutors") },
                    label = { Text("Mis Tutores") },
                    selected = false,
                    onClick = { /* Navegación a Mis Tutores */ }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Surface {
                Image(
                    painter = painterResource(id = R.drawable.perfil_fondo),
                    contentDescription = "Fondo de pantalla",
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    SearchBar(
                        query = searchQuery,
                        onQueryChanged = { newQuery -> searchQuery = newQuery },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = { isDropdownVisible = !isDropdownVisible },
                        colors = ButtonDefaults.buttonColors(AzulPrimario),
                    ) {
                        Text(
                            text = if (isDropdownVisible) "Ocultar Materias" else "Mostrar Materias",
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = if (isDropdownVisible) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (isDropdownVisible) "Ocultar" else "Mostrar"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isDropdownVisible) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp) // Limita la altura máxima
                        ) {
                            items(filteredMaterias) { materia ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            searchQuery = materia.nombre
                                            isDropdownVisible = false
                                        },
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Text(
                                        text = materia.nombre,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(tutors) { tutor ->
                            TutorCard(
                                name = tutor.nombre,
                                descripcion = tutor.descripcion,
                                fotoPerfil = tutor.fotoPerfilUrl,
                                materias = tutor.materiasIds // Cambiado a tutor.materiasIds
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = { newText ->
            onQueryChanged(newText)
        },
        placeholder = {
            Text(text = "Buscar...")
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun TutorCard(
    name: String,
    descripcion: String,
    fotoPerfil: String,
    materias: List<String>
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.tutor), // Reemplaza con la lógica para cargar imágenes
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
            )
            Column {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = descripcion, style = MaterialTheme.typography.bodyMedium)

                Row {
                    materias.forEach { materia ->
                        Text(text = materia, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainEstudiantePreview() {
    // Puedes hacer un preview más detallado si necesitas
}
