package com.pegbeer.postsapp.data.repository

import com.pegbeer.postsapp.core.Result
import com.pegbeer.postsapp.core.SortMode
import com.pegbeer.postsapp.data.model.Comment
import com.pegbeer.postsapp.data.model.Post
import com.pegbeer.postsapp.data.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository(private val apiService: ApiService) {

    fun getAllPostsResult() = flow {
        emit(Result.loading())
        val data = apiService.getAllPosts()
        if(data != null){
            if(data.isNotEmpty()){
                emit(Result.success(data))
            }
        }else{
            emit(Result.success(emptyList()))
        }
    }.flowOn(Dispatchers.IO)

    fun getPhotosById(id:Int)= flow{
        val data = apiService.getPhotosByPost(id)
        if(data != null){
            if(data.isNotEmpty()){
                emit(Result.success(data))
            }
        }else{
            emit(Result.success(emptyList()))
        }
    }.flowOn(Dispatchers.IO)

    fun getCommentsById(id: Int) = flow {
        val data = apiService.getCommentsByPost(id)
        if(data != null){
            if(data.isNotEmpty()){
                emit(Result.success(data))
            }
        }else{
            emit(Result.success(emptyList()))
        }
    }.flowOn(Dispatchers.IO)

}