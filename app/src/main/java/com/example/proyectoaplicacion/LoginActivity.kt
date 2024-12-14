package com.example.proyectoaplicacion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoaplicacion.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        auth = FirebaseAuth.getInstance()

        checkLoggedInUser()
        initEvent()
    }

    private fun checkLoggedInUser() {
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val acceso = sharedPref.getString("acceso", null)
        if (acceso != null) {
            navigateToMainActivity()
        }
    }

    private fun initEvent() {
        bindingLogin.loginButton.setOnClickListener {
            logIn()
        }

        bindingLogin.newUserButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        bindingLogin.recoverPasswordButton.setOnClickListener {
            val user = bindingLogin.usernameEditText.text.toString()
            if (user.isNotEmpty()) {
                val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
                val username = sharedPref.getString("userName", null)
                val email = sharedPref.getString("userEmail", null)

                if (user == username) {
                    if (email != null) {
                        recoverPassword(email)
                    }
                } else {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Debes ingresar tu usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logIn() {
        val input = bindingLogin.usernameEditText.text.toString()
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("userEmail", null)
        val username = sharedPref.getString("userName", null)
        var emailToUse: String? = null

        if (input == username) {
            emailToUse = email
        }

        if (emailToUse != null) {
            val password = bindingLogin.passwordEditText.text.toString()
            auth.signInWithEmailAndPassword(emailToUse, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user?.isEmailVerified == true) {
                            saveUserToPreferences(emailToUse, input)
                            navigateToMainActivity()
                        } else {
                            Toast.makeText(
                                this,
                                "Por favor verifica tu correo electrónico antes de iniciar sesión",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(this, "Error al iniciar sesión: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Usuario o correo no encontrado", Toast.LENGTH_SHORT).show()
        }
    }


    private fun recoverPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToPreferences(email: String, username: String) {
        val acceso = "concedido"
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("userEmail", email)
            .putString("userName", username)
            .putString("acceso", acceso)
            .apply()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
