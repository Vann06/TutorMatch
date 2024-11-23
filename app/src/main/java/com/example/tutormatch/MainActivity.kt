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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tutormatch.estructuras.firebaseImplementation.Estudiante1
import com.example.tutormatch.navigation.Navigation
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.estudiante.Main.View.MainEstudiante
import com.example.tutormatch.ui.estudiante.Main.ViewModel.MainEstudianteViewModel
import com.example.tutormatch.ui.estudiante.Tutor_Es.View.PerfilTutorEstudianteScreen
import com.example.tutormatch.ui.general.Login.View.LoginScreen
import com.example.tutormatch.ui.general.SignUp.View.SignUpScreen
import com.example.tutormatch.ui.general.bienvenida.View.Bienvenida
import com.example.tutormatch.ui.theme.TutorMatchTheme
import com.example.tutormatch.ui.tutor.MisTutorias.View.MisTutoriasScreen

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
                        navController = navController,
                        viewModel = estudianteViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
