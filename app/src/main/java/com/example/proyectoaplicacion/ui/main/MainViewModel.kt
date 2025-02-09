package com.example.proyectoaplicacion.ui.main

import androidx.lifecycle.ViewModel
import com.example.proyectoaplicacion.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun isUserLoggedIn(): Boolean = userRepository.isUserLoggedIn()

    fun logout() {
        userRepository.logout()
    }
}