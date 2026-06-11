package com.locototeam.core.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.locototeam.core.room.entity.ApiTimestampEntity

@Dao
abstract class ApiTimestampDao : UtilDao<ApiTimestampEntity> {

    @Query(
        """
           SELECT * FROM apitimestampentity where apiKey = :apiKey 
        """
    )
    abstract suspend fun readApiTimestampEntity(apiKey: String): ApiTimestampEntity
}
