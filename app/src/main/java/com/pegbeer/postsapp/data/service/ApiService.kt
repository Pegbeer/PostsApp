package com.pegbeer.postsapp.data.service

import com.pegbeer.postsapp.data.model.Photo
import com.pegbeer.postsapp.data.model.Post
import com.pegbeer.postsapp.data.network.IApiClient

class ApiService(private val apiClient:IApiClient){


    suspend fun getAllPosts():List<Post>?{
        val response = apiClient.getAllPosts()
        return if(response.isSuccessful){
            response.body()
        }else{
            emptyList()
        }
    }

    suspend fun getPhotosByPost(id:Int):List<Photo>?{
        val response = apiClient.getPhotosByPost(id)
        return if(response.isSuccessful){
            response.body()
        }else{
            emptyList()
        }
    }

}