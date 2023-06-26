package com.locotodevteam.seprecempresas.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.view.detail.DetailNetworkRepository
import com.locotodevteam.seprecempresas.view.search.SearchDatabaseRepository
import com.locotodevteam.seprecempresas.view.search.SearchNetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    const val BASE_URL = "https://servicios.seprec.gob.bo/"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun providesRetrofitInstance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                okhttp3.OkHttpClient.Builder()
                    .addInterceptor(providesHttpLoggingInterceptor())
                    .addInterceptor(ChuckerInterceptor.Builder(context).build())
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesCompanyService(retrofit: Retrofit): CompanyService {
        return retrofit.create(CompanyService::class.java)
    }

    @Singleton
    @Provides
    fun providesSearchNetworkRepository(
        companyService: CompanyService,
        searchDatabaseRepository: SearchDatabaseRepository
    ): SearchNetworkRepository {
        return SearchNetworkRepository(companyService, searchDatabaseRepository)
    }

    @Singleton
    @Provides
    fun providesDetailNetworkRepository(companyService: CompanyService): DetailNetworkRepository {
        return DetailNetworkRepository(companyService)
    }

}