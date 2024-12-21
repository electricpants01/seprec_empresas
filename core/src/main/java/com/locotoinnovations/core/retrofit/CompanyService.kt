package com.locotoinnovations.core.retrofit

import com.locotoinnovations.core.model.Company
import com.locotoinnovations.core.model.CompanyDetail
import com.locotoinnovations.core.model.TramiteDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CompanyService {
    @GET("api/empresas/buscarEmpresas")
    suspend fun getCompany(
        @Query("filtro") searchCompany: String,
        @Query("limite") limit: String = "30",
        @Query("pagina") pagina: String = "1"
    ): Response<Company>

    @GET("api/empresas/informacionDirectorioEmpresa/{companyId}/establecimiento/{companyId}")
    suspend fun getCompanyDetail(@Path("companyId") companyId: String): Response<CompanyDetail>

    @GET("api/tramites/buscarTramite")
    suspend fun getTramiteDetail(@Query("codigo") tramiteId: String): Response<TramiteDetail>
}