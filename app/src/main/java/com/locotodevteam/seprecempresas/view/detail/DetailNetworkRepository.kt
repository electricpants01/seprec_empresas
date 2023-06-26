package com.locotodevteam.seprecempresas.view.detail

import com.locotodevteam.seprecempresas.retrofit.CompanyService
import com.locotodevteam.seprecempresas.retrofit.ConnectionResult
import java.net.UnknownHostException
import javax.inject.Inject

class DetailNetworkRepository @Inject constructor(
    private val companyService: CompanyService
) {

    suspend fun getCompanyDetail(companyId: String): ConnectionResult {
        return try {
            val response = companyService.getCompanyDetail(companyId)
            if (response.isSuccessful) {
                response.body().let { details ->
                    ConnectionResult.NetworkSuccess(details)
                }
            } else {
                ConnectionResult.NetworkFailure("No se encontró el detalle de la empresa")
            }
        } catch (e: UnknownHostException) {
            ConnectionResult.NetworkFailure("No hay conexión a internet")
        }
    }

}