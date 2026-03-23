package com.pamelaibarra.genesisai.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pamelaibarra.genesisai.data.local.entity.DrugEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DrugDao {
    @Query("SELECT * FROM drugs ORDER BY name ASC")
    fun observeAll(): Flow<List<DrugEntity>>

    @Query("SELECT * FROM drugs WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): DrugEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<DrugEntity>)
}


