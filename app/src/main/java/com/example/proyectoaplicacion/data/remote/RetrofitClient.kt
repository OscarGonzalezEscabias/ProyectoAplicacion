package com.example.proyectoaplicacion.data.remote

import com.example.proyectoaplicacion.data.local.SharedPreferencesHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) {

    private val BASE_URL = "http://172.21.16.1:8080"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val token = sharedPreferencesHelper.getAccessToken()
            if (token != null) {
                request.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(request.build())
        }
        .build()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}