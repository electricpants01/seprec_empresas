package com.locotodevteam.seprecempresas.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.locotodevteam.seprecempresas.room.dao.CompanyDao
import com.locotodevteam.seprecempresas.room.entity.CompanyEntity


@Database(entities = [CompanyEntity::class], version = 1, exportSchema = false)
abstract class SeprecDatabase: RoomDatabase() {

    abstract fun companyDao(): CompanyDao
}