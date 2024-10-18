package com.example.tutormatch.ui.general.SignUp.ViewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.tutormatch.navigation.NavigationState
import com.example.tutormatch.ui.general.SignUp.Repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage

    fun signUp(email: String, password: String, name: String, accountType: String, navController: NavHostController) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = authRepository.signUp(email, password, name, accountType)
            result.onSuccess { userId ->
                _successMessage.value = "Registro exitoso!"

                // Redirección basada en el tipo de cuenta
                if (accountType == "Estudiante") {
                    navController.navigate(NavigationState.Main_Es.route)
                } else if (accountType == "Tutor") {
                    navController.navigate(NavigationState.Tutor_Es.route)
                }

            }.onFailure {
                _errorMessage.value = it.message
            }
            _isLoading.value = false
        }
    }

    fun setError(message: String) {
        _errorMessage.value = message
    }
}
