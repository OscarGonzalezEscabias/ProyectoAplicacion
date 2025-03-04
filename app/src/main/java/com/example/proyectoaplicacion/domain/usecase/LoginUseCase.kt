package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun execute(usernameOrEmail: String, password: String): String? {
        return userRepository.login(usernameOrEmail, password)
    }
}