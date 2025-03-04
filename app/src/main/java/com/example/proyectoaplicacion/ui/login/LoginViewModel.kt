package com.example.proyectoaplicacion.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoaplicacion.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> get() = _navigateToMain

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val token = userRepository.login(username, password)
            if (token != null) {
                _navigateToMain.value = true
            } else {
                _errorMessage.value = "Error en el inicio de sesión"
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return userRepository.isUserLoggedIn()
    }

    fun recoverPassword(username: String) {
        viewModelScope.launch {
            val email = userRepository.getCurrentUserEmail()
            if (email != null) {
                _errorMessage.value = "Correo de recuperación enviado a $email"
            } else {
                _errorMessage.value = "Nombre de usuario no encontrado"
            }
        }
    }
}