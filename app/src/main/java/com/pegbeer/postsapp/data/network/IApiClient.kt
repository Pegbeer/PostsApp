package com.pegbeer.postsapp.data.network

import com.pegbeer.postsapp.data.model.Comment
import com.pegbeer.postsapp.data.model.Photo
import com.pegbeer.postsapp.data.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IApiClient {

    @GET("/posts")
    suspend fun getAllPosts():Response<List<Post>>

    @GET("/posts/{id}/comments")
    suspend fun getCommentsByPost(
        @Path("id")id:Int
    ):Response<List<Comment>>

    @GET("/posts/{id}/photos")
    suspend fun getPhotosByPost(
        @Path("id")id:Int
    ):Response<List<Photo>>
}