package com.locotodevteam.seprecempresas.retrofit
import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    companion object {
        fun getRetrofitInstance(context: Context): Retrofit {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(NetworkConnectionInterceptor(context))
            // return Retrofit.Builder()
            return Retrofit.Builder()
                .baseUrl("https://servicios.seprec.gob.bo/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
        }
    }
}