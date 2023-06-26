package com.locotodevteam.seprecempresas.view.search

import com.locotodevteam.seprecempresas.model.Company
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import java.net.UnknownHostException
import javax.inject.Inject

class SearchNetworkRepository @Inject constructor(
    private val companyService: CompanyService,
    private val searchDatabaseRepository: SearchDatabaseRepository
) {

    suspend fun searchCompany(companyName: String): ConnectionResult {
        return try {
            val response = companyService.getCompany(companyName)
            if (response?.isSuccessful == true) {
                val body = response?.body()
                body.let { body ->
                    searchDatabaseRepository.insertAllCompanies(body as Company)
                    ConnectionResult.NetworkSuccess(body)
                }
            } else {
                ConnectionResult.NetworkFailure("No se encontró la empresa")
            }
        } catch (e: UnknownHostException) {
            ConnectionResult.NetworkFailure("No hay conexión a internet")
        }
    }
}