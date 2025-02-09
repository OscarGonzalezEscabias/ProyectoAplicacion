package com.example.proyectoaplicacion.domain.repository

interface UserRepository {
    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit)
    fun signUp(email: String, password: String, onComplete: (Boolean, String?) -> Unit)
    fun saveUser(email: String, username: String)
    fun getCurrentUserEmail(): String?
    fun getCurrentUserName(): String?
    fun isUserLoggedIn(): Boolean
    fun logout()
}