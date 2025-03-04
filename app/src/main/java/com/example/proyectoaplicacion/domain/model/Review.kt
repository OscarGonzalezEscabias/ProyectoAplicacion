package com.example.proyectoaplicacion.domain.model

data class Review(
    val id: Int,
    val title: String,
    val description: String,
    val image: String? = null
)