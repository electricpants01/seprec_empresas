package com.locototeam.core.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.locototeam.core.room.SeprecDatabase
import com.locototeam.core.room.dao.ApiTimestampDao
import com.locototeam.core.room.dao.CompanyDao
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
        return Room.databaseBuilder(
            context = context,
            klass = SeprecDatabase::class.java,
            name = SeprecDatabase.DATABASE_NAME
        ).addMigrations(
            SeprecDatabase.MIGRATION_1_2
        ).build()
    }

    @Singleton
    @Provides
    fun providesCompanyDao(db: SeprecDatabase): CompanyDao = db.companyDao()

    @Singleton
    @Provides
    fun providesApiTimestampDao(db: SeprecDatabase): ApiTimestampDao = db.apiTimestampDao()

//    @Singleton
//    @Provides
//    fun providesSearchDatabaseRepository(companyDao: CompanyDao): SearchDatabaseRepository {
//        return SearchDatabaseRepository(companyDao)
//    }
}