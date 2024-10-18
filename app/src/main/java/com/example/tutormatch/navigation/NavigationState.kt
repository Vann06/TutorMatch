package com.example.tutormatch.navigation


sealed class NavigationState(val route: String) {
    data object Bienvenida: NavigationState("Bienvenida")
    data object Login : NavigationState("Login")
    data object SignUp : NavigationState("SignUp")

    //ESTUDIANTE
    data object Main_Es: NavigationState("Main_Es")
    data object MyTutors: NavigationState("MyTutors")
    data object Perfil_Es: NavigationState("Perfil_Es")
    data object SolicitudTutoria: NavigationState("SolicitudTutoria")
    data object Tutor_Es: NavigationState("Tutor_Es")

    //TUTOR

}