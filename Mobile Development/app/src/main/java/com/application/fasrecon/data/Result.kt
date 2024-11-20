package com.application.fasrecon.data

import com.application.fasrecon.util.WrapMessage

sealed class Result<out T> private constructor() {
    data object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: WrapMessage<String?>) : Result<Nothing>()
}