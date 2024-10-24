package com.khaledamin.prayerapp.utils

sealed class State<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : State<T>()
    class Success<T>(data: T?, message: String? = null) : State<T>(data,message)
    class Error<T>(data: T? = null, message: String?) : State<T>(data,message)
}