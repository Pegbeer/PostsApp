package com.pegbeer.postsapp

import android.app.Application
import com.pegbeer.postsapp.di.AppModule
import com.pegbeer.postsapp.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class PostsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@PostsApp)
            modules(listOf(NetworkModule.modules, AppModule.modules))
        }
    }
}