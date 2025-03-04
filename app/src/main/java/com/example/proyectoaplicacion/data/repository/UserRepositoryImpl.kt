package com.example.proyectoaplicacion.data.repository

import com.example.proyectoaplicacion.data.local.SharedPreferencesHelper
import com.example.proyectoaplicacion.data.remote.ApiService
import com.example.proyectoaplicacion.data.remote.LoginRequest
import com.example.proyectoaplicacion.data.remote.RegisterRequest
import com.example.proyectoaplicacion.data.remote.RetrofitClient
import com.example.proyectoaplicacion.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : UserRepository {

    private val apiService: ApiService = retrofitClient.instance.create(ApiService::class.java)

    override suspend fun login(usernameOrEmail: String, password: String): String? {
        val response = apiService.login(LoginRequest(usernameOrEmail, password))
        return if (response.isSuccessful) {
            val token = response.body()?.token
            if (token != null) {
                sharedPreferencesHelper.saveAccessToken(token) // Guardar el token en SharedPreferences
            }
            token
        } else {
            null
        }
    }

    override suspend fun register(username: String, email: String, password: String): Boolean {
        val response = apiService.register(RegisterRequest(username, email, password))
        return response.isSuccessful
    }

    override fun saveUser(email: String, username: String) {
        sharedPreferencesHelper.saveUser(email, username, "concedido")
    }

    override fun getCurrentUserEmail(): String? {
        return sharedPreferencesHelper.getUserEmail()
    }

    override fun getCurrentUserName(): String? {
        return sharedPreferencesHelper.getUserName()
    }

    override fun isUserLoggedIn(): Boolean {
        return sharedPreferencesHelper.getAccessToken() != null
    }

    override fun logout() {
        sharedPreferencesHelper.clearAccessToken()
    }
}