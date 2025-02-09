package com.example.proyectoaplicacion.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectoaplicacion.data.local.SharedPreferencesHelper
import com.example.proyectoaplicacion.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val sharedPreferencesHelper: SharedPreferencesHelper // Inyectar SharedPreferencesHelper
) : ViewModel() {

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun signUp(email: String, password: String, username: String) {
        signInUseCase.execute(email, password) { success, error ->
            if (success) {
                sharedPreferencesHelper.saveUser(email, username, "concedido")
                _navigateToLogin.value = true
            } else {
                _errorMessage.value = error
            }
        }
    }
}