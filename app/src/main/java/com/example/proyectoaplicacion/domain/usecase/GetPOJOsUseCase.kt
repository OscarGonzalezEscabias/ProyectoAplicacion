package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.model.POJO
import com.example.proyectoaplicacion.domain.repository.POJORepository
import javax.inject.Inject

class GetPOJOsUseCase @Inject constructor(private val pojoRepository: POJORepository) {
    fun execute(): List<POJO> = pojoRepository.getPOJOs()
}
