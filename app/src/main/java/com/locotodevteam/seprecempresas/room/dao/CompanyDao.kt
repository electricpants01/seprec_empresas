package com.locotodevteam.seprecempresas.room.dao

import androidx.room.*
import com.locotodevteam.seprecempresas.room.entity.CompanyEntity

@Dao
abstract class CompanyDao: UtilDao<CompanyEntity> {

    @Query("SELECT * FROM company")
    abstract fun getAll(): List<CompanyEntity>

    @Query("SELECT * FROM company WHERE razonSocial LIKE '%' || :companyName || '%'")
    abstract fun searchCompany(companyName: String): List<CompanyEntity>
}
