package com.example.proyectoaplicacion.data.repository

import com.example.proyectoaplicacion.data.model.Review as DataReview
import com.example.proyectoaplicacion.domain.model.Review as DomainReview
import com.example.proyectoaplicacion.data.remote.ApiService
import com.example.proyectoaplicacion.data.remote.RetrofitClient
import com.example.proyectoaplicacion.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient
) : ReviewRepository {

    private val apiService: ApiService = retrofitClient.instance.create(ApiService::class.java)

    override suspend fun getReviews(): List<DomainReview> {
        val dataReviews = apiService.getReviews().body() ?: emptyList()
        return dataReviews.map { it.toDomainReview() }
    }

    override suspend fun addReview(review: DomainReview): Boolean {
        val dataReview = review.toDataReview()
        return apiService.addReview(dataReview).isSuccessful
    }

    override suspend fun editReview(id: Int, review: DomainReview): Boolean {
        val dataReview = review.toDataReview()
        return apiService.editReview(id, dataReview).isSuccessful
    }

    override suspend fun deleteReview(id: Int): Boolean {
        return apiService.deleteReview(id).isSuccessful
    }

    private fun DataReview.toDomainReview(): DomainReview {
        return DomainReview(
            id = this.id,
            title = this.title,
            description = this.description,
            image = this.image
        )
    }

    private fun DomainReview.toDataReview(): DataReview {
        return DataReview(
            id = this.id,
            title = this.title,
            description = this.description,
            image = this.image
        )
    }
}