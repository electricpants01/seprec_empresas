package com.locotodevteam.seprecempresas.retrofit

class NoConnectivityException: Exception() {
    override val message: String?
        get() = "No internet connection :("
}