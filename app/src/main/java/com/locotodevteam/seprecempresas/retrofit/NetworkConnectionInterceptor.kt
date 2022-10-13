package com.locotodevteam.seprecempresas.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import com.locotodevteam.seprecempresas.R
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isInternetAvailable()) {
           // throw NoConnectivityException()   // shows a message that there is no internet connection
            val builder = request.newBuilder()
            builder.header("Cache-Control", "public, only-if-cached, max-stale=2419200") // is used to cache the data for 4 weeks
            request = builder.build()
            return chain.proceed(request)
        }
        // add caching to the request
        val builder = request.newBuilder()
        builder.header("Cache-Control", "public, max-age=" + 5) // 5 seconds
        return chain.proceed(request)
    }
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
