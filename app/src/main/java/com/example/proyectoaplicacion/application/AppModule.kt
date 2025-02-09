package com.example.proyectoaplicacion.application

import android.content.Context
import com.example.proyectoaplicacion.data.local.SharedPreferencesHelper
import com.example.proyectoaplicacion.data.remote.FirebaseDataSource
import com.example.proyectoaplicacion.data.repository.POJORepositoryImpl
import com.example.proyectoaplicacion.data.repository.UserRepositoryImpl
import com.example.proyectoaplicacion.domain.repository.POJORepository
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
    fun provideFirebaseDataSource(): FirebaseDataSource = FirebaseDataSource()

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(@ApplicationContext context: Context): SharedPreferencesHelper =
        SharedPreferencesHelper(context)

    @Provides
    @Singleton
    fun provideUserRepository(
        firebaseDataSource: FirebaseDataSource,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): UserRepository = UserRepositoryImpl(firebaseDataSource, sharedPreferencesHelper)

    @Provides
    @Singleton
    fun providePOJORepository(): POJORepository = POJORepositoryImpl()
}