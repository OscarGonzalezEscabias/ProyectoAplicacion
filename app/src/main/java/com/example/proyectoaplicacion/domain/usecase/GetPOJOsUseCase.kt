package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.model.Review
import com.example.proyectoaplicacion.domain.repository.ReviewRepository
import javax.inject.Inject

class GetPOJOsUseCase @Inject constructor(private val reviewRepository: ReviewRepository) {
    suspend fun execute(): List<Review> {
        return reviewRepository.getReviews()
    }
}