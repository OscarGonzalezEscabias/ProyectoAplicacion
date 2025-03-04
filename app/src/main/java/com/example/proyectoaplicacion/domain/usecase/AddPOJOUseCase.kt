package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.model.Review
import com.example.proyectoaplicacion.domain.repository.ReviewRepository
import javax.inject.Inject

class AddPOJOUseCase @Inject constructor(private val reviewRepository: ReviewRepository) {
    suspend fun execute(review: Review): Boolean {
        return reviewRepository.addReview(review)
    }
}