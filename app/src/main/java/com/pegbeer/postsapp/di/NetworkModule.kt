package com.pegbeer.postsapp.di

import com.pegbeer.postsapp.data.network.IApiClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    val modules = module {
        single { provideRetrofit() }
        single { provideApiClient(get()) }
    }

    private fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideApiClient(retrofit: Retrofit) = retrofit.create(IApiClient::class.java)
}