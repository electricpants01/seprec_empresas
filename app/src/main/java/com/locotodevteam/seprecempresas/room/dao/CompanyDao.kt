package com.locotodevteam.seprecempresas.room.dao

import androidx.room.*
import com.locotodevteam.seprecempresas.room.entity.CompanyEntity

@Dao
interface CompanyDao {

    @Query("SELECT * FROM company")
    fun getAll(): List<CompanyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(company: CompanyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(companies: List<CompanyEntity>?)

    @Query("SELECT * FROM company WHERE razonSocial LIKE '%' || :companyName || '%'")
    fun searchCompany(companyName: String): List<CompanyEntity>
}
