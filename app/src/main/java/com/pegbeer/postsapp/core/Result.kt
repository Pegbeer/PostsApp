package com.pegbeer.postsapp.core

data class Result<out T>(val status: Status,val data:T?, val message:String?){


    companion object{
        fun <T> success(data:T?):Result<T>{
            return Result(Status.SUCCESS,data,null)
        }

        fun <T> error(message: String, error: Error?): Result<T> {
            return Result(Status.ERROR, null, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }

}
