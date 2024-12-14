package com.example.proyectoaplicacion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoaplicacion.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var bindingSignIn: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingSignIn = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(bindingSignIn.root)

        auth = FirebaseAuth.getInstance()

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
        val username = bindingSignIn.usernameEditText.text.toString()
        val email = bindingSignIn.emailEditText.text.toString()
        val password = bindingSignIn.passwordEditText.text.toString()
        val repeatPassword = bindingSignIn.repeatPasswordEditText.text.toString()

        if (password == repeatPassword && password.isNotBlank() && email.isNotBlank() && username.isNotBlank()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.sendEmailVerification()?.addOnCompleteListener { verifyTask ->
                            if (verifyTask.isSuccessful) {
                                saveUserToPreferences(email, username)
                                navigateToLogin()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error al enviar correo de verificación: ${verifyTask.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Error al registrar usuario: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden o hay campos vacíos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserToPreferences(email: String, username: String) {
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("userEmail", email)
            .putString("userName", username)
            .apply()
    }


    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
