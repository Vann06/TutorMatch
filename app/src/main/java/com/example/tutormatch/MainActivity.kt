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
import com.example.tutormatch.navigation.Navigation
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.estudiante.Main.View.MainEstudiante
import com.example.tutormatch.ui.estudiante.Main.ViewModel.MainEstudianteViewModel
import com.example.tutormatch.ui.general.Login.View.LoginScreen
import com.example.tutormatch.ui.general.SignUp.View.SignUpScreen
import com.example.tutormatch.ui.general.bienvenida.View.Bienvenida
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.tutormatch.ui.theme.TutorMatchTheme

class MainActivity : ComponentActivity() {

    private lateinit var estudianteViewModel: MainEstudianteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (applicationContext as MyApp).estudianteRepository
        estudianteViewModel = MainEstudianteViewModel(repository)

        setContent {
            TutorMatchTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(
                        navController = navController, // Llama a la función Navigation
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun NavigationGraph(navController: NavHostController, viewModel: MainEstudianteViewModel, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavigationState.Bienvenida.route, // Cambia a usar la clase NavigationState
        modifier = modifier
    ) {
        composable(NavigationState.Bienvenida.route) { Bienvenida(navController) }
        composable(NavigationState.Login.route) { LoginScreen(navController) }
        composable(NavigationState.SignUp.route) { SignUpScreen(navController) }
        composable(NavigationState.Main_Es.route) { // Cambia a usar la clase NavigationState
            MainEstudiante(navController, viewModel)
        }
        // Asegúrate de agregar las demás rutas según sea necesario
    }
}
