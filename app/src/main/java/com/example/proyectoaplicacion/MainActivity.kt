package com.example.proyectoaplicacion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initEvent()

    }

    private fun initEvent() {
        Toast.makeText(this, "Ingresado correctamente al MainActivity", Toast.LENGTH_SHORT).show()
    }
}