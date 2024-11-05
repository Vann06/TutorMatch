package com.example.tutormatch.navigation


sealed class NavigationState(val route: String) {
    object Bienvenida : NavigationState("Bienvenida")
    object Login : NavigationState("Login")
    object SignUp : NavigationState("SignUp")

    // ESTUDIANTE
    object Main_Es : NavigationState("Main_Es")
    object MyTutors : NavigationState("MyTutors")
    object Perfil_Es : NavigationState("Perfil_Es")
    object SolicitudTutoria : NavigationState("SolicitudTutoria")
    object Tutor_Es : NavigationState("Tutor_Es")

    object PerfilTutorEstudiante : NavigationState("PerfilTutorEstudiante/{tutorId}") {
        fun createRoute(tutorId: String) = "PerfilTutorEstudiante/$tutorId"
    }

    // TUTOR
    object CrearTutoria : NavigationState("CrearTutoria")
    object MisTutorias : NavigationState("MisTutorias")
    object PerfilTutor : NavigationState("Perfil_Tu")
}
