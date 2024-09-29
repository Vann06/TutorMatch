package com.example.tutormatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.tutormatch.ui.theme.TutorMatchTheme
import com.example.tutormatch.ui.vistas.estudiante.MainEstudiante
import com.example.tutormatch.ui.vistas.estudiante.PerfilEstudianteScreen

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

    val tutor = Tutor(nombre = "Juan Perez", materias = mutableListOf(allMaterias[0], allMaterias[1]), fotoPerfil = R.drawable.estudiante, myStudents = mutableListOf(), descripcion = "descripcion", modalidad = "modalidad" )

    val estudiante = Estudiante(nombre = "Ricardo Godinez", usuario = "Ricgo_01", myTutors = mutableListOf(tutor))

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



