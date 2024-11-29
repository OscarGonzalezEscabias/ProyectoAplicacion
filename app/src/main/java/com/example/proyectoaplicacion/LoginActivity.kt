package com.example.proyectoaplicacion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoaplicacion.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        initEvent()
    }

    private fun initEvent() {
        bindingLogin.loginButton.setOnClickListener {
            logIn()
        }

        bindingLogin.newUserButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logIn() {
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", null)
        val password = sharedPref.getString("password", null)
        val userNameInput = bindingLogin.usernameEditText.text.toString()
        val passwordInput = bindingLogin.passwordEditText.text.toString()

        if ((userName != null) && (password != null)) {
            when {
                userNameInput != userName && passwordInput != password -> {
                    Toast.makeText(this, "Usuario y contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
                userNameInput != userName -> {
                    Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show()
                }
                passwordInput != password -> {
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        } else {
            Toast.makeText(this, "No se ha registrado ningún usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
