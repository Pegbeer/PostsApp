package com.pegbeer.postsapp.ui.listener

import org.koin.androidx.scope.ScopeActivity

interface IOnPostButtonClickListener {
    fun <T:ScopeActivity> onClick(destination:Class<T>, id:Int)
}