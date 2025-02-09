package com.example.proyectoaplicacion.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)

    fun saveUser(email: String, username: String, access: String) {
        sharedPref.edit().apply {
            putString("userEmail", email)
            putString("userName", username)
            putString("acceso", access)
            putString("username_$username", email)
            apply()
        }
    }

    fun getUserEmail(): String? = sharedPref.getString("userEmail", null)
    fun getUserName(): String? = sharedPref.getString("userName", null)
    fun getAccess(): String? = sharedPref.getString("acceso", null)
    fun getEmailByUsername(username: String): String? {
        return sharedPref.getString("username_$username", null)
    }
    fun clearAccess() {
        sharedPref.edit().remove("acceso").apply()
    }
}