package com.example.proyectoaplicacion.domain.repository

interface UserRepository {
    suspend fun login(usernameOrEmail: String, password: String): String?
    suspend fun register(username: String, email: String, password: String): Boolean
    fun saveUser(email: String, username: String)
    fun getCurrentUserEmail(): String?
    fun getCurrentUserName(): String?
    fun isUserLoggedIn(): Boolean
    fun logout()
}