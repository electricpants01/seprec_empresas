package com.locotoinnovations.core.repository

import com.locotoinnovations.core.retrofit.NetworkResult

interface ICompanyService {
    suspend fun getCompany(searchCompany: String): NetworkResult
    suspend fun getCompanyDetail(companyId: String): NetworkResult
    suspend fun getTramiteDetail(tramiteId: String): NetworkResult
}