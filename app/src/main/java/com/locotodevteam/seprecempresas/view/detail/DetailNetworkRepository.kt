package com.locotodevteam.seprecempresas.view.detail

import com.locotodevteam.seprecempresas.R
import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.NetworkResult
import java.net.UnknownHostException
import javax.inject.Inject

class DetailNetworkRepository @Inject constructor(
    private val companyService: CompanyService
) {

    suspend fun getCompanyDetail(companyId: String): NetworkResult {
        return try {
            val response = companyService.getCompanyDetail(companyId)
            if (response.isSuccessful) {
                response.body().let { details ->
                    NetworkResult.NetworkSuccess(details)
                }
            } else {
                NetworkResult.NetworkFailure(R.string.company_search_not_found)
            }
        } catch (e: UnknownHostException) {
            NetworkResult.NetworkFailure(R.string.network_error)
        }
    }

}