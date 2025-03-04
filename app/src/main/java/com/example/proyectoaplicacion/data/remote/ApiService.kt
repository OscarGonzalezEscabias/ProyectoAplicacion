package com.example.proyectoaplicacion.data.remote

import com.example.proyectoaplicacion.data.model.Review
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("reviews")
    suspend fun getReviews(): Response<List<Review>>

    @POST("reviews/add")
    suspend fun addReview(@Body review: Review): Response<Unit>

    @PATCH("reviews/edit/{id}")
    suspend fun editReview(@Path("id") id: Int, @Body review: Review): Response<Unit>

    @DELETE("reviews/del/{id}")
    suspend fun deleteReview(@Path("id") id: Int): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<Unit>
}

data class LoginRequest(val usernameOrEmail: String, val password: String)
data class LoginResponse(val token: String)
data class RegisterRequest(val username: String, val email: String, val password: String)