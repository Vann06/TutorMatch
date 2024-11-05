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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.tutormatch.R
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.estudiante.Main.Repository.EstudianteRepository
import com.example.tutormatch.ui.estudiante.Main.ViewModel.MainEstudianteViewModel
import com.example.tutormatch.ui.estudiante.Main.ViewModel.MainEstudianteViewModelFactory
import com.example.tutormatch.ui.theme.AzulPrimario
import com.google.firebase.firestore.FirebaseFirestore
import com.example.tutormatch.ui.estudiante.Tutor_Es.View.PerfilTutorEstudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainEstudiante(
    navController: NavHostController,
    viewModel: MainEstudianteViewModel = viewModel(
        factory = MainEstudianteViewModelFactory(EstudianteRepository(FirebaseFirestore.getInstance()))
    )
)
{
    val materias by viewModel.materias.collectAsState()
    val materiasMap by viewModel.materiasMap.collectAsState()
    val tutors by viewModel.tutors.collectAsState()
    val materiaSeleccionada by viewModel.materiaSeleccionada.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var isDropdownVisible by remember { mutableStateOf(false) }

    val filteredMaterias = materias.filter { materia ->
        materia.nombre.contains(searchQuery, ignoreCase = true)
    }.toMutableList()

    val tutorsFiltrados = if (materiaSeleccionada != null) {
        tutors.filter { tutor ->
            tutor.materias.contains(materiaSeleccionada)
        }
    } else {
        tutors
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { navController.navigate(NavigationState.Perfil_Es.route) {
                        launchSingleTop = true
                        restoreState = true
                    } }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Buscador") },
                    label = { Text("Buscador") },
                    selected = true,
                    onClick = { /* Ya estamos en esta pantalla */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "MyTutors") },
                    label = { Text("Mis Tutores") },
                    selected = false,
                    onClick = { navController.navigate(NavigationState.MyTutors.route) {
                        launchSingleTop = true
                        restoreState = true
                    }}
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
                                .heightIn(max = 200.dp)
                        ) {
                            items(filteredMaterias) { materia ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            searchQuery = materia.nombre
                                            isDropdownVisible = false
                                            viewModel.setMateriaSeleccionada(materia.id)
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
                            // Opción para mostrar todos los tutores
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            searchQuery = ""
                                            isDropdownVisible = false
                                            viewModel.setMateriaSeleccionada(null)
                                        },
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Text(
                                        text = "Mostrar Todos",
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
                        items(tutorsFiltrados) { tutor ->
                            TutorCard(
                                name = tutor.nombre,
                                descripcion = tutor.descripcion,
                                fotoPerfil = tutor.fotoPerfilUrl,
                                materiasIds = tutor.materias,
                                materiasMap = materiasMap,
                                onClick = {
                                    navController.navigate(NavigationState.PerfilTutorEstudiante.createRoute(tutor.id))
                                }
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
    materiasIds: List<String>,
    materiasMap: Map<String, String>,
    onClick: () -> Unit

) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (fotoPerfil.isNotEmpty()) {
                    rememberAsyncImagePainter(fotoPerfil)
                } else {
                    painterResource(id = R.drawable.tutor)
                },
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp)
            )
            Column {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = descripcion, style = MaterialTheme.typography.bodyMedium)

                Row {
                    materiasIds.forEach { materiaNombre ->
                        Text(
                            text = materiaNombre,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(end = 4.dp)
                        )
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
