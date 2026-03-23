package com.pamelaibarra.genesisai.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pamelaibarra.genesisai.data.local.entity.VademecumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VademecumDao {
    @Query("SELECT * FROM vademecum ORDER BY drugName ASC")
    fun observeAll(): Flow<List<VademecumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<VademecumEntity>)
}


