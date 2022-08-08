package com.example.vismamusic.common

sealed class RepoResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): RepoResult<T>(data)
    class Success<T>(data: T): RepoResult<T>(data)
    class Error<T>(message: String, data: T? = null): RepoResult<T>(data, message)
}
