package com.example.tutormatch.navigation


sealed class NavigationState(val route: String) {
    object Bienvenida : NavigationState("Bienvenida")
    object Login : NavigationState("Login")
    object SignUp : NavigationState("SignUp")

    // ESTUDIANTE
    object Main_Es : NavigationState("Main_Es")
    object MyTutors : NavigationState("MyTutors")
    object Perfil_Es : NavigationState("Perfil_Es")
    object SolicitudTutoria : NavigationState("SolicitudTutoria/{tutorId}?tutoriaId={tutoriaId}") {
        fun createRoute(tutorId: String, tutoriaId: String? = null): String {
            return if (tutoriaId != null) {
                "SolicitudTutoria/$tutorId?tutoriaId=$tutoriaId"
            } else {
                "SolicitudTutoria/$tutorId"
            }
        }
    }

    //object Tutor_Es : NavigationState("Tutor_Es")
    object PerfilTutorEstudiante : NavigationState("PerfilTutorEstudiante/{tutorId}") {
        fun createRoute(tutorId: String) = "PerfilTutorEstudiante/$tutorId"
    }
    object TutoriaDetalleEstudiante : NavigationState("TutoriaDetalleEstudiante/{tutoriaId}") {
        fun createRoute(tutoriaId: String) = "TutoriaDetalleEstudiante/$tutoriaId"
    }

    // TUTOR
    object CrearTutoria : NavigationState("CrearTutoria")
    object MisTutorias : NavigationState("MisTutorias")
    object PerfilTutor : NavigationState("Perfil_Tu")
    object Estudiante_Tu : NavigationState("Estudiante_Tu/{tutoriaId}") {
        fun createRoute(tutoriaId: String) = "Estudiante_Tu/$tutoriaId"
    }
}
