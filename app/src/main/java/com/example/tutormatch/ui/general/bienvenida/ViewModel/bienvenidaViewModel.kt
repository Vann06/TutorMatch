package com.example.tutormatch.ui.general.bienvenida.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tutormatch.ui.general.bienvenida.Repository.BienvenidaRepository

class BienvenidaViewModel(private val bienvenidaRepository: BienvenidaRepository) : ViewModel() {
    fun login(username: String, password: String): LiveData<Boolean> {
        return bienvenidaRepository.login(username, password)
    }

    fun signUp(username: String, password: String): LiveData<Boolean> {
        return bienvenidaRepository.signUp(username, password)
    }
}
