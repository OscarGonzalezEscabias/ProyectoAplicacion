package com.example.proyectoaplicacion.data.repository

import com.example.proyectoaplicacion.data.local.SharedPreferencesHelper
import com.example.proyectoaplicacion.data.remote.FirebaseDataSource
import com.example.proyectoaplicacion.domain.repository.UserRepository

class UserRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : UserRepository {
    override fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        firebaseDataSource.signIn(email, password) { success, error ->
            if (success) {
                sharedPreferencesHelper.saveUser(email, email, "concedido")
                onComplete(true, null)
            } else {
                onComplete(false, error)
            }
        }
    }

    override fun signUp(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        firebaseDataSource.signUp(email, password, onComplete)
    }


    override fun saveUser(email: String, username: String) {
        sharedPreferencesHelper.saveUser(email, username, "concedido")
    }

    override fun getCurrentUserEmail(): String? = sharedPreferencesHelper.getUserEmail()
    override fun getCurrentUserName(): String? = sharedPreferencesHelper.getUserName()
    override fun isUserLoggedIn(): Boolean {
        return sharedPreferencesHelper.getAccess() != null && firebaseDataSource.getCurrentUser() != null
    }
    override fun logout() {
        sharedPreferencesHelper.clearAccess()
        firebaseDataSource.logout()
    }
}