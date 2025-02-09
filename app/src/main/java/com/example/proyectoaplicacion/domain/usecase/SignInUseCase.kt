package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun execute(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        userRepository.signUp(email, password, onComplete)
    }
}
