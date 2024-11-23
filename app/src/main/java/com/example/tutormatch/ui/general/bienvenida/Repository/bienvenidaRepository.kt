package com.example.tutormatch.ui.general.bienvenida.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BienvenidaRepository {
    fun login(username: String, password: String): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        isSuccess.value = true
        return isSuccess
    }

    fun signUp(username: String, password: String): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        isSuccess.value = true
        return isSuccess
    }
}
