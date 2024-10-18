package com.example.tutormatch

import android.app.Application
import com.example.tutormatch.ui.estudiante.Main.Repository.EstudianteRepository
import com.example.tutormatch.ui.general.SignUp.Repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MyApp : Application() {

    lateinit var auth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore

    lateinit var authRepository: AuthRepository
    lateinit var estudianteRepository: EstudianteRepository

    override fun onCreate() {
        super.onCreate()

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Inicializar repositorios
        authRepository = AuthRepository(auth, firestore)
        estudianteRepository = EstudianteRepository(firestore)
    }
}
