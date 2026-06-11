package com.locototeam.core.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


interface UtilDao<Entity> {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: Entity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(entity: Entity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(entity: Entity): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateOrAbort(entity: Entity): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrReplace(entity: Entity): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateOrIgnore(entity: Entity): Int

    @Delete
    suspend fun delete(entity: Entity): Int
}