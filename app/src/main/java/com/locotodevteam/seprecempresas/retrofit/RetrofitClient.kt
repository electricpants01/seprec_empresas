package com.locotodevteam.seprecempresas.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    companion object {
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://servicios.seprec.gob.bo/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}