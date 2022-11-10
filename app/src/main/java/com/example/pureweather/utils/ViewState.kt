package com.example.pureweather.utils

sealed class ViewState

object SuccessState: ViewState()

object ErrorState: ViewState()