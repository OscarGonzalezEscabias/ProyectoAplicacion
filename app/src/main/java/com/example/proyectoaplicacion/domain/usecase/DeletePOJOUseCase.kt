package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.repository.ReviewRepository
import javax.inject.Inject

class DeletePOJOUseCase @Inject constructor(private val reviewRepository: ReviewRepository) {
    suspend fun execute(id: Int): Boolean {
        return reviewRepository.deleteReview(id)
    }
}