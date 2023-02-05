package com.pegbeer.postsapp.di

import androidx.lifecycle.ViewModel
import com.pegbeer.postsapp.data.repository.PostRepository
import com.pegbeer.postsapp.ui.view.AlbumActivity
import com.pegbeer.postsapp.ui.view.MainActivity
import com.pegbeer.postsapp.ui.viewmodel.AlbumsViewModel
import com.pegbeer.postsapp.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule{

    val modules = module {
        scope<MainActivity> {
            viewModel { MainViewModel(get()) }
        }
        scope<AlbumActivity> {
            viewModel{ AlbumsViewModel(get()) }
        }
    }
}