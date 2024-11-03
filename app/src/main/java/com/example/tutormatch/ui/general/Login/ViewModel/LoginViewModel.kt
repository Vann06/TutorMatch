package com.example.tutormatch.ui.general.Login.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.tutormatch.ui.general.Login.Repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: LoginRepository = LoginRepository()
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    var accountType: String? = null
        private set

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login(navController: NavHostController) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            val result = repository.login(email.value, password.value)
            result.onSuccess { userAccountType ->
                accountType = userAccountType
                _successMessage.value = "Login exitoso"
            }.onFailure { exception ->
                _errorMessage.value = exception.message
            }
            _isLoading.value = false
        }
    }

    fun resetSuccessMessage() {
        _successMessage.value = null
    }
}
