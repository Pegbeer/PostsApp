package com.pegbeer.postsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pegbeer.postsapp.core.Result
import com.pegbeer.postsapp.data.model.Photo
import com.pegbeer.postsapp.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AlbumsViewModel(
    private val repository: PostRepository
) : ViewModel() {


    private val _photos = MutableStateFlow<Result<List<Photo>>>(Result.loading())
    val photos get() = _photos.asStateFlow()


    fun load(id:Int){
        viewModelScope.launch {
            repository.getPhotosByPost(id).collect{
                _photos.emit(it)
            }
        }
    }

}