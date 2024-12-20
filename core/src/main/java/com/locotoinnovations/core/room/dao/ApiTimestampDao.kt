package com.locotoinnovations.core.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.locotoinnovations.core.room.entity.ApiTimestampEntity

@Dao
abstract class ApiTimestampDao: UtilDao<ApiTimestampEntity> {

    @Query(
        """
           SELECT * FROM apitimestampentity where apiKey = :apiKey 
        """
    )
    abstract suspend fun readApiTimestampEntity(apiKey: String): ApiTimestampEntity
}