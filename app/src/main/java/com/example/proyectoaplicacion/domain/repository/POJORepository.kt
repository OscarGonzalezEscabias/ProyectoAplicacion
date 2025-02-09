package com.example.proyectoaplicacion.domain.repository

import com.example.proyectoaplicacion.domain.model.POJO

interface POJORepository {
    fun getPOJOs(): List<POJO>
    fun addPOJO(pojo: POJO)
    fun deletePOJO(pojo: POJO)
    fun editPOJO(pojo: POJO)
}