package com.locotodevteam.seprecempresas.view.search

import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.model.Company
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.NetworkResult
import java.net.UnknownHostException
import javax.inject.Inject

class SearchNetworkRepository @Inject constructor(
    private val companyService: CompanyService,
    private val searchDatabaseRepository: SearchDatabaseRepository
) {

    suspend fun searchCompany(companyName: String): NetworkResult {
        return try {
            val response = companyService.getCompany(companyName)
            if (response.isSuccessful) {
                val body = response.body()
                body.let { body ->
                    if (body?.datos?.filas?.isNullOrEmpty() == true) {
                        NetworkResult.NetworkEmptyResult(body as Company)
                    } else {
                        searchDatabaseRepository.insertAllCompanies(body as Company)
                        NetworkResult.NetworkSuccess(body)
                    }
                }
            } else {
                NetworkResult.NetworkFailure(R.string.company_search_not_found)
            }
        } catch (e: UnknownHostException) {
            NetworkResult.NetworkFailure(R.string.network_error)
        }
    }
}