package com.locototeam.core.room.dao

import androidx.room.Delete
import androidx.room.Entity
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

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entities: List<Entity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(entities: List<Entity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(entities: List<Entity>): List<Long>

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateOrAbort(entity: Entity): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrReplace(entity: Entity): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateOrIgnore(entity: Entity): Int

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateOrAbort(entities: List<Entity>): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrReplace(entities: List<Entity>): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateOrIgnore(entities: List<Entity>): Int

    @Delete
    abstract suspend fun delete(entity: Entity): Int

    @Delete
    suspend fun delete(entities: List<Entity>): Int
}