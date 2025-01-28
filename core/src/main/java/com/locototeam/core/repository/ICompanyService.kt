package com.locototeam.core.repository

import com.locototeam.core.retrofit.NetworkResult

interface ICompanyService {
    suspend fun getCompany(searchCompany: String): NetworkResult
    suspend fun getCompanyDetail(companyId: String): NetworkResult
    suspend fun getTramiteDetail(tramiteId: String): NetworkResult
}