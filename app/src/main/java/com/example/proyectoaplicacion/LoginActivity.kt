package com.example.proyectoaplicacion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var btnNewUser: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.loginButton)
        btnNewUser = findViewById(R.id.newUserButton)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        initEvent()

    }

    private fun initEvent() {
        btnLogin.setOnClickListener {
            logIn()
        }

        btnNewUser.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logIn() {
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("userName", null)
        val password = sharedPref.getString("password", null)
        val userNameEditText = usernameEditText.text.toString()
        val passwordEditText = passwordEditText.text.toString()

        if ((userName != null) && (password != null)) {
            if ((userNameEditText != userName) && (passwordEditText != password)) {
                Toast.makeText(this, "Usuario y contraseña incorrectos", Toast.LENGTH_SHORT).show()
            } else if (userNameEditText != userName) {
                Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show()
            } else if (passwordEditText != password) {
                Toast.makeText(this, "Contraseña incorrecto", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        } else {
            Toast.makeText(this, "No se ha registrado ningun usuario", Toast.LENGTH_SHORT).show()
        }
    }
}