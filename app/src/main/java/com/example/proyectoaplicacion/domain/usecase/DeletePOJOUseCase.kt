package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.model.POJO
import com.example.proyectoaplicacion.domain.repository.POJORepository
import javax.inject.Inject

class DeletePOJOUseCase @Inject constructor(private val pojoRepository: POJORepository) {
    fun execute(pojo: POJO) {
        pojoRepository.deletePOJO(pojo)
    }
}
