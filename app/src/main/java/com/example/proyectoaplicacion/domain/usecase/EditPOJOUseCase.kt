package com.example.proyectoaplicacion.domain.usecase

import com.example.proyectoaplicacion.domain.model.Review
import com.example.proyectoaplicacion.domain.repository.ReviewRepository
import javax.inject.Inject

class EditPOJOUseCase @Inject constructor(private val reviewRepository: ReviewRepository) {
    suspend fun execute(id: Int, review: Review): Boolean {
        return reviewRepository.editReview(id, review)
    }
}