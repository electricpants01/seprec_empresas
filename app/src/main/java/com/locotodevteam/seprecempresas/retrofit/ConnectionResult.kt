package com.locotodevteam.seprecempresas.retrofit

sealed class ConnectionResult {
    data class NetworkSuccess<T>(val data: T) : ConnectionResult()
    data class NetworkFailure(val message: String) : ConnectionResult()
}
