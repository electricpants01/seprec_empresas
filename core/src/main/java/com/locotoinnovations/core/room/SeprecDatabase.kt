package com.locotoinnovations.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.locotoinnovations.core.room.dao.ApiTimestampDao
import com.locotoinnovations.core.room.dao.CompanyDao
import com.locotoinnovations.core.room.entity.ApiTimestampEntity
import com.locotoinnovations.core.room.entity.CompanyEntity


@Database(
    entities = [
        CompanyEntity::class,
        ApiTimestampEntity::class
    ],
    autoMigrations = [
    ],
    version = 2,
    exportSchema = true
)
abstract class SeprecDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "seprec_database"
    }

    abstract fun companyDao(): CompanyDao
    abstract fun apiTimestampDao(): ApiTimestampDao

    object MIGRATION_1_2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                    create table ${ApiTimestampEntity.TABLE_NAME} (
                        id text primary key not null,
                        apiKey text not null,
                        lastSyncedTimestamp text not null
                    )
                """.trimIndent()
            )
        }
    }
}