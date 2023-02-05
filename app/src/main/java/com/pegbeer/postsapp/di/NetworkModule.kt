package com.pegbeer.postsapp.di

import com.pegbeer.postsapp.data.network.IApiClient
import com.pegbeer.postsapp.data.repository.PostRepository
import com.pegbeer.postsapp.data.service.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"

    val modules = module {
        single { provideRetrofit() }
        single { provideApiClient(get()) }
        single { provideApiService(get()) }
        single { providePostRepository(get ()) }
    }

    private fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideApiClient(retrofit: Retrofit) = retrofit.create(IApiClient::class.java)

    private fun provideApiService(client:IApiClient) = ApiService(client)

    private fun providePostRepository(apiService: ApiService) = PostRepository(apiService)
}