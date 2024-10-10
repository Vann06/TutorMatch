package com.example.tutormatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tutormatch.estructuras.Estudiante
import com.example.tutormatch.estructuras.Materias
import com.example.tutormatch.estructuras.Tutor
import com.example.tutormatch.estructuras.Tutoria
import com.example.tutormatch.ui.theme.TutorMatchTheme
import com.example.tutormatch.ui.estudiante.Main.View.MainEstudiante

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorMatchTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    val allMaterias = mutableListOf(
        Materias("Matemáticas"),
        Materias("Física"),
        Materias("Química"),
        Materias("Historia"),
        Materias("Geografía")
    )

    // Crear un tutor de ejemplo
    val tutor = Tutor(
        nombre = "Juan Perez",
        materias = mutableListOf(allMaterias[0], allMaterias[1]),
        fotoPerfil = R.drawable.estudiante, // Asegúrate de que R.drawable.estudiante sea un recurso válido
        myStudents = mutableListOf(),
        descripcion = "Descripcion",
        modalidad = "Modalidad"
    )

    // Crear una lista de tutorías de ejemplo
    val tutorias = mutableListOf(
        Tutoria(
            id = "1",
            fecha = "2024-10-01",
            hora = "10:00 AM",
            modalidad = "Online",
            tutor = tutor,
            materia = allMaterias[0] // Por ejemplo, Matemáticas
        ),
        Tutoria(
            id = "2",
            fecha = "2024-10-02",
            hora = "11:00 AM",
            modalidad = "Presencial",
            tutor = tutor,
            materia = allMaterias[1] // Por ejemplo, Física
        )
    )

    // Crear un estudiante de ejemplo
    val estudiante = Estudiante(
        nombre = "Ricardo Godinez",
        usuario = "Ricgo_01",
        misTutorias = tutorias, // Aquí asignas la lista de tutorías
        fotoPerfil = "" // Asigna una imagen de perfil si es necesario
    )

    NavHost(
        navController = navController,
        startDestination = "MainEstudiante",
        modifier = modifier
    ) {
        composable("MainEstudiante") {
            MainEstudiante(
                navController = navController,
                estudiante = estudiante,
                tutor = tutor,
                allMaterias = allMaterias
            )
        }
    }
}




