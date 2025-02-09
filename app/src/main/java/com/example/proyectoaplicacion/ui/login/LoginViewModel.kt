package com.example.proyectoaplicacion.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectoaplicacion.data.local.SharedPreferencesHelper
import com.example.proyectoaplicacion.data.remote.FirebaseDataSource
import com.example.proyectoaplicacion.domain.repository.UserRepository
import com.example.proyectoaplicacion.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val firebaseDataSource: FirebaseDataSource,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> get() = _navigateToMain

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun login(username: String, password: String) {
        val email = sharedPreferencesHelper.getEmailByUsername(username)
        if (email == null) {
            _errorMessage.value = "Nombre de usuario no encontrado"
        } else {
            loginUseCase.execute(email, password) { success, error ->
                if (success) {
                    _navigateToMain.value = true
                } else {
                    _errorMessage.value = error
                }
            }
        }
    }

    fun recoverPassword(username: String) {
        val email = sharedPreferencesHelper.getEmailByUsername(username)
        if (email == null) {
            _errorMessage.value = "Nombre de usuario no encontrado"
        } else {
            firebaseDataSource.sendPasswordResetEmail(email) { success, error ->
                if (success) {
                    _errorMessage.value = "Correo de recuperación enviado"
                } else {
                    _errorMessage.value = error
                }
            }
        }
    }

    fun isUserLoggedIn(): Boolean = userRepository.isUserLoggedIn()

}