package com.example.tutormatch.navigation


sealed class NavigationState(val route: String) {
    data object Bienvenida: NavigationState("Bienvenida")

}