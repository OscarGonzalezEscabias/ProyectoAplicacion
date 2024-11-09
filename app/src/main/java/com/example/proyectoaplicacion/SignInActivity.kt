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

class SignInActivity : AppCompatActivity() {
    private lateinit var btnCreate: Button
    private lateinit var btnRegisteredUser: Button
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var usernameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        btnCreate = findViewById(R.id.createButton)
        btnRegisteredUser = findViewById(R.id.registeredUserButton)
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText)

        initEvent()

    }

    private fun initEvent() {
        btnCreate.setOnClickListener{
            saveUser()
        }

        btnRegisteredUser.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUser() {
        if ((passwordEditText.text.toString() == repeatPasswordEditText.text.toString()) && (passwordEditText.text.toString() != "") && (repeatPasswordEditText.text.toString() != "")) {
            val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
            sharedPref.edit().putString("userName", usernameEditText.text.toString()).putString("password", passwordEditText.text.toString()).apply()

            Toast.makeText(this, "Usuario guardado exitosamente", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
    }
}