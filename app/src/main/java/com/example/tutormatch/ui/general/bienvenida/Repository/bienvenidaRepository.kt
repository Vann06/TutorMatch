package com.example.tutormatch.ui.general.bienvenida.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BienvenidaRepository {
    fun login(username: String, password: String): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        // Aquí puedes agregar la lógica para conectarte a un backend o base de datos
        isSuccess.value = true // Simulamos que siempre es exitoso
        return isSuccess
    }

    fun signUp(username: String, password: String): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        // Simulación del registro en el backend
        isSuccess.value = true
        return isSuccess
    }
}
