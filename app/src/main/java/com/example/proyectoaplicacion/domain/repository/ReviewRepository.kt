package com.example.proyectoaplicacion.domain.repository

import com.example.proyectoaplicacion.domain.model.Review

interface ReviewRepository {
    suspend fun getReviews(): List<Review>
    suspend fun addReview(review: Review): Boolean
    suspend fun editReview(id: Int, review: Review): Boolean
    suspend fun deleteReview(id: Int): Boolean
}