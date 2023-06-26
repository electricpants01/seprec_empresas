package com.locotodevteam.seprecempresas.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.locotodevteam.seprecempresas.room.SeprecDatabase
import com.locotodevteam.seprecempresas.room.dao.CompanyDao
import com.locotodevteam.seprecempresas.view.search.SearchDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): SeprecDatabase {
        val db = Room.databaseBuilder(
            context,
            SeprecDatabase::class.java,
            "seprec_database"
        ).build()
        return db
    }

    @Singleton
    @Provides
    fun providesCompanyDao(db: SeprecDatabase): CompanyDao = db.companyDao()

    @Singleton
    @Provides
    fun providesSearchDatabaseRepository(companyDao: CompanyDao): SearchDatabaseRepository {
        return SearchDatabaseRepository(companyDao)
    }
}