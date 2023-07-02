package com.locotodevteam.seprecempresas.view.search

import com.locotodevteam.seprecempresas.entityUtils.toCompany
import com.locotodevteam.seprecempresas.entityUtils.toCompanyEntity
import com.locotodevteam.seprecempresas.model.Company
import com.locotodevteam.seprecempresas.retrofit.NetworkResult
import com.locotodevteam.seprecempresas.room.dao.CompanyDao
import com.locotodevteam.seprecempresas.room.entity.CompanyEntity
import javax.inject.Inject


class SearchDatabaseRepository @Inject constructor(
    private val companyDao: CompanyDao
) {

    fun insertAllCompanies(companies: Company) {
        val companiesEntity = companies.toCompanyEntity()
        companyDao.insertAll(companiesEntity)
    }

    fun getAllCompanies(): List<CompanyEntity> {
        return companyDao.getAll()
    }

    fun searchCompany(companyName: String): NetworkResult {
        return companyDao.searchCompany(companyName).let { companies ->
            if (companies.isNotEmpty()) {
                NetworkResult.NetworkSuccess(companies.toCompany())
            } else {
                NetworkResult.NetworkEmptyResult(companies.toCompany())
            }
        }
    }

}