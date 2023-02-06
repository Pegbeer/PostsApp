package com.pegbeer.postsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pegbeer.postsapp.data.repository.PostRepository
import com.pegbeer.postsapp.core.Result
import com.pegbeer.postsapp.data.model.Comment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<Result<List<Comment>>>(Result.loading())
    val comments get() = _comments.asStateFlow()

    val searchQuery = MutableLiveData("")


    fun load(id:Int){
        viewModelScope.launch {
            repository.getCommentsById(id).collect{
                _comments.emit(it)
            }
        }
    }

}