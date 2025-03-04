package com.example.proyectoaplicacion.application

import android.content.Context
import com.example.proyectoaplicacion.data.local.SharedPreferencesHelper
import com.example.proyectoaplicacion.data.remote.RetrofitClient
import com.example.proyectoaplicacion.data.repository.ReviewRepositoryImpl
import com.example.proyectoaplicacion.data.repository.UserRepositoryImpl
import com.example.proyectoaplicacion.domain.repository.ReviewRepository
import com.example.proyectoaplicacion.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(@ApplicationContext context: Context): SharedPreferencesHelper =
        SharedPreferencesHelper(context)

    @Provides
    @Singleton
    fun provideRetrofitClient(sharedPreferencesHelper: SharedPreferencesHelper): RetrofitClient {
        return RetrofitClient(sharedPreferencesHelper)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        retrofitClient: RetrofitClient,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): UserRepository {
        return UserRepositoryImpl(retrofitClient, sharedPreferencesHelper)
    }

    @Provides
    @Singleton
    fun provideReviewRepository(retrofitClient: RetrofitClient): ReviewRepository {
        return ReviewRepositoryImpl(retrofitClient)
    }
}