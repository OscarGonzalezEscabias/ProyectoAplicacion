package com.example.proyectoaplicacion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoaplicacion.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var bindingSignIn: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingSignIn = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(bindingSignIn.root)

        initEvent()
    }

    private fun initEvent() {
        bindingSignIn.createButton.setOnClickListener {
            saveUser()
        }

        bindingSignIn.registeredUserButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUser() {
        val password = bindingSignIn.passwordEditText.text.toString()
        val repeatPassword = bindingSignIn.repeatPasswordEditText.text.toString()
        val username = bindingSignIn.usernameEditText.text.toString()

        if (password == repeatPassword && password.isNotBlank()) {
            val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
            sharedPref.edit().putString("userName", username).putString("password", password).apply()

            Toast.makeText(this, "Usuario guardado exitosamente", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }
}
