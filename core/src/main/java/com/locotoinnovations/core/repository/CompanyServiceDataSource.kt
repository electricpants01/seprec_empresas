package com.locotoinnovations.core.repository

import com.locotoinnovations.core.R
import com.locotoinnovations.core.model.Company
import com.locotoinnovations.core.retrofit.CompanyService
import com.locotoinnovations.core.retrofit.NetworkResult
import java.net.UnknownHostException
import javax.inject.Inject

class CompanyServiceDataSource @Inject constructor(
    private val companyService: CompanyService
) : ICompanyService {
    override suspend fun getCompany(
        searchCompany: String,
    ): NetworkResult {
        return try {
            val response = companyService.getCompany(searchCompany)
            if (response.isSuccessful) {
                val body = response.body()
                body.let { company ->
                    if (body?.datos?.filas?.isEmpty() == true) {
                        NetworkResult.NetworkEmptyResult(company as Company)
                    } else {
                        NetworkResult.NetworkSuccess(company as Company)
                    }
                }
            } else {
                NetworkResult.NetworkFailure(R.string.company_search_not_found)
            }
        } catch (e: UnknownHostException) {
            NetworkResult.NetworkFailure(R.string.network_error)
        }
    }

    override suspend fun getCompanyDetail(companyId: String): NetworkResult {
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

    override suspend fun getTramiteDetail(tramiteId: String): NetworkResult {
        return try {
            val response = companyService.getTramiteDetail(tramiteId)
            if (response.isSuccessful) {
                response.body().let { detail ->
                    NetworkResult.NetworkSuccess(detail)
                }
            } else {
                // we get code() == 412
                NetworkResult.NetworkFailure(R.string.tramite_not_found)
            }
        } catch (e: UnknownHostException) {
            NetworkResult.NetworkFailure(R.string.network_error)
        }
    }

}