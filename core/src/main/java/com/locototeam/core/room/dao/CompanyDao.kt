package com.locototeam.core.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.locototeam.core.room.entity.CompanyEntity

@Dao
abstract class CompanyDao: UtilDao<CompanyEntity> {

    @Query("SELECT * FROM company")
    abstract fun getAll(): List<CompanyEntity>

    @Query("SELECT * FROM company WHERE razonSocial LIKE '%' || :companyName || '%'")
    abstract fun searchCompany(companyName: String): List<CompanyEntity>
}
