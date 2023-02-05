package com.pegbeer.postsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pegbeer.postsapp.core.Result
import com.pegbeer.postsapp.data.model.Post
import com.pegbeer.postsapp.data.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts:MutableStateFlow<Result<List<Post>>> = MutableStateFlow(Result.loading())
    val posts get() = _posts.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            repository.getAllPostsResult().collect{
                _posts.emit(it)
            }
        }
    }
}