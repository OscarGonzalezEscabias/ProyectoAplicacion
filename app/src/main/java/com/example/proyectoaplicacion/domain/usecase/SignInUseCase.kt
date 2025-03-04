package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.repository.UserRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(username: String, email: String, password: String): Boolean {
        return userRepository.register(username, email, password)
    }
}