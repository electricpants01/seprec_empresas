package com.locototeam.core.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = ApiTimestampEntity.TABLE_NAME,
)
data class ApiTimestampEntity(
    @ColumnInfo(name = Column.API_KEY) val apiKey: String,
    @ColumnInfo(name = Column.LAST_SYNCED_TIMESTAMP) val lastSyncedTimestamp: String,
    @PrimaryKey() @ColumnInfo(name = Column.ID) val id: String = UUID.randomUUID().toString(),
) {
    companion object {
        const val TABLE_NAME = "ApiTimestampEntity"
    }

    internal object Column {
        const val API_KEY = "apiKey"
        const val LAST_SYNCED_TIMESTAMP = "lastSyncedTimestamp"
        const val ID = "id"
    }
}