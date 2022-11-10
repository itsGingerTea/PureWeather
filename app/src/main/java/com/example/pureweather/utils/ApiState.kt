package com.example.pureweather.utils

import java.lang.Exception

sealed class ApiState<out Any>

class Success<T>(val data: T) : ApiState<T>()

class Error(var e: Exception? = null, var message: String? = null) : ApiState<Nothing>()

object Loading : ApiState<Nothing>()
