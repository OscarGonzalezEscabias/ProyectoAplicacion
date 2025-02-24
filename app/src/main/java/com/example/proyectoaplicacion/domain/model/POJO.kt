package com.example.proyectoaplicacion.domain.model

data class POJO(
    val imageResId: Int,
    val title: String,
    val description: String,
    val imageBase64: String? = null
)