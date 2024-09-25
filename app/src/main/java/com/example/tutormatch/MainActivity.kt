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
import com.example.tutormatch.ui.theme.TutorMatchTheme
import com.example.tutormatch.ui.vistas.estudiante.PerfilEstudianteScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
fun MainScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "PerfilScreen") {
        composable("PerfilScreen") {
            PerfilEstudianteScreen(
                estudiante = Estudiante(nombre = "Ricardo Godinez", usuario = "Ricgo_01"),
                onMyTutorsClick = {}
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            PerfilEstudianteScreen(
                estudiante = Estudiante(nombre = "Ricardo Godinez", usuario = "Ricgo_01"),
                onMyTutorsClick = {}
            )
        }
    }
}